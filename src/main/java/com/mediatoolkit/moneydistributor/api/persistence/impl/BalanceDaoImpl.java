package com.mediatoolkit.moneydistributor.api.persistence.impl;

import com.mediatoolkit.moneydistributor.api.persistence.BalanceDao;
import com.mediatoolkit.moneydistributor.api.persistence.entity.Balance;
import com.mediatoolkit.moneydistributor.api.persistence.entity.GroupBalance;
import com.mediatoolkit.moneydistributor.api.persistence.entity.UserEntity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Id value of a first user always needs to by smaller then id of a second user.
 * Every user id pair is unique and corresponding amount represents how much money one user
 * owes the other:
 * if amount < 0 : first user owes that amount of money to second user
 * if amount > 0 : second user owes that amount of money to first user
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BalanceDaoImpl implements BalanceDao {
    private static final Logger LOG = LoggerFactory.getLogger(BalanceDao.class);

    private static final BalanceEntityRowMapper POSITIVE_ROW_MAPPER = new BalanceEntityRowMapper(false);
    private static final BalanceEntityRowMapper NEGATIVE_ROW_MAPPER = new BalanceEntityRowMapper(true);

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void increment(Long firstUserId, Long secondUserId, Double amount) {
        increment(Collections.singletonList(new BalanceDaoEntry(firstUserId, secondUserId, amount)));
    }

    /**
     * It will be better to delete row when amount == 0;
     * Maybe adding trigger, something like this:
     * CREATE TRIGGER delete_if_amount_zero
     * AFTER UPDATE
     * ON moneydistributor.balance_tbl
     * FOR EACH ROW
     * BEGIN
     * DELETE FROM moneydistributor.balance_tbl WHERE amount = 0.0;
     * end;
     */
    @Override
    public void increment(List<BalanceDaoEntry> balanceEntries) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO moneydistributor.balance_tbl (first_user_id, second_user_id, amount) " +
                "VALUES (?, ?, ?) " +
                "   ON DUPLICATE KEY UPDATE amount = amount + ?;",
            new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    BalanceDaoEntry balanceDaoEntry = balanceEntries.get(i);
                    ps.setLong(1, balanceDaoEntry.getFirstUserId());
                    ps.setLong(2, balanceDaoEntry.getSecondUserId());
                    ps.setDouble(3, balanceDaoEntry.getAmount());
                    ps.setDouble(4, balanceDaoEntry.getAmount());
                    LOG.debug(balanceDaoEntry.toString());
                }

                @Override
                public int getBatchSize() {
                    return balanceEntries.size();
                }
            });
        LOG.info("Balances saved.");
    }

    @Override
    public List<Balance> getBalancesAsLender(Long lenderId) {
        List<Balance> first = jdbcTemplate.query(
            "SELECT u.id, u.first_name, u.last_name, u.username, u.email, b.amount " +
                "FROM moneydistributor.balance_tbl b " +
                "LEFT JOIN moneydistributor.user_tbl u ON b.second_user_id = u.id " +
                "WHERE first_user_id = ? AND b.amount > 0.0;",
            args(lenderId),
            POSITIVE_ROW_MAPPER);

        List<Balance> second = jdbcTemplate.query(
            "SELECT u.id, u.first_name, u.last_name, u.username, u.email, b.amount " +
                "FROM moneydistributor.balance_tbl b " +
                "LEFT JOIN moneydistributor.user_tbl u ON b.first_user_id = u.id " +
                "WHERE second_user_id = ? AND b.amount < 0.0;",
            args(lenderId),
            NEGATIVE_ROW_MAPPER);

        LOG.info("Balances for lender retried.");
        return Stream.concat(first.stream(), second.stream()).collect(Collectors.toList());
    }

    @Override
    public List<Balance> getBalancesAsBorrower(Long borrowerId) {
        List<Balance> first = jdbcTemplate.query(
            "SELECT u.id, u.first_name, u.last_name, u.username, u.email, b.amount " +
                "FROM moneydistributor.balance_tbl b " +
                "LEFT JOIN moneydistributor.user_tbl u ON b.second_user_id = u.id " +
                "WHERE first_user_id = ? AND b.amount < 0.0;",
            args(borrowerId),
            NEGATIVE_ROW_MAPPER);

        List<Balance> second = jdbcTemplate.query(
            "SELECT u.id, u.first_name, u.last_name, u.username, u.email, b.amount " +
                "FROM moneydistributor.balance_tbl b " +
                "LEFT JOIN moneydistributor.user_tbl u ON b.first_user_id = u.id " +
                "WHERE second_user_id = ? AND b.amount > 0.0;",
            args(borrowerId),
            POSITIVE_ROW_MAPPER);

        LOG.info("Balances for borrower retried.");
        return Stream.concat(first.stream(), second.stream()).collect(Collectors.toList());
    }

    //todo: make it better
    @Override
    public List<GroupBalance> getBalancesBetweenUsers(List<Long> users) {
        Collections.sort(users);

        List<GroupBalance> balances = new ArrayList<>();
        int i, j;
        int size = users.size();
        for (i = 0; i < size - 1; i++) { //first user, smaller id-s
            for (j = i + 1; j < size; j++) { //second user
                Long firstUserId = users.get(i);
                Long secondUserId = users.get(j);
                Double amount;
                try {
                    amount = jdbcTemplate.queryForObject(
                        "SELECT b.amount " +
                            "FROM moneydistributor.balance_tbl b " +
                            "WHERE first_user_id = ? AND second_user_id = ?",
                        Double.class,
                        firstUserId,
                        secondUserId);
                } catch (EmptyResultDataAccessException e) {
                    LOG.debug("", e);
                    amount = null;
                }

                if (amount != null && amount > 0) {
                    balances.add(getGroupBalance(firstUserId, secondUserId, amount));
                }
            }
        }

        LOG.debug(balances.toString());
        LOG.info("Balance for group retried.");
        return balances;
    }

    private Object[] args(Object... args) {
        return args;
    }

    @Data
    public static class BalanceDaoEntry {
        private final Long firstUserId;
        private final Long secondUserId;
        private final Double amount;

        @Override
        public String toString() {
            return "first_user_id= " + firstUserId + " second_user_id=" + secondUserId + " amount=" + amount;
        }
    }

    private GroupBalance getGroupBalance(Long firstUser, Long secondUser, Double amount) {
        return amount < 0
            ? new GroupBalance(secondUser, firstUser, -1 * amount)
            : new GroupBalance(firstUser, secondUser, amount);

    }

    private static class BalanceEntityRowMapper implements RowMapper<Balance> {

        private final int sign;

        public BalanceEntityRowMapper(boolean inverse) {
            this.sign = inverse ? -1 : 1;
        }

        @Override
        public Balance mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Balance(
                new UserEntity(
                    rs.getLong(1),   // Id
                    rs.getString(2), // First name
                    rs.getString(3), // Last name
                    rs.getString(4), // Username
                    rs.getString(5)  // Email
                ),
                sign * rs.getDouble(6)  // Amount
            );
        }
    }
}
