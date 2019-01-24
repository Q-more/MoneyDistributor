package com.mediatoolkit.moneydistributor.api.service;

import com.mediatoolkit.moneydistributor.api.exceptions.TransactionException;
import com.mediatoolkit.moneydistributor.api.exceptions.UserException;
import com.mediatoolkit.moneydistributor.api.exceptions.enums.ApiErrorCode;
import com.mediatoolkit.moneydistributor.api.model.Transaction;
import com.mediatoolkit.moneydistributor.api.persistence.BalanceDao;
import com.mediatoolkit.moneydistributor.api.persistence.TransactionDao;
import com.mediatoolkit.moneydistributor.api.persistence.entity.Balance;
import com.mediatoolkit.moneydistributor.api.persistence.entity.GroupBalance;
import com.mediatoolkit.moneydistributor.api.persistence.entity.TransactionEntity;
import com.mediatoolkit.moneydistributor.api.persistence.impl.BalanceDaoImpl;
import com.mediatoolkit.moneydistributor.api.service.util.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Transactions service for managing transactions between users.
 * Also this service is taking care of their balances.
 *
 * @author lucija
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService {

    private final TransactionDao transactionDao;
    private final BalanceDao balanceDao;
    private final TimeProvider timeProvider;

    public void save(Transaction transaction) {
        saveAll(Collections.singletonList(transaction));
    }

    public void saveAll(List<Transaction> transactions) {
        try {
            transactionDao.saveAll(
                transactions.stream()
                    .map(this::mapToTransactionEntity)
                    .collect(Collectors.toList())
            );
            balanceDao.increment(getBalancesFromTransactions(transactions));
        } catch (DataIntegrityViolationException ex) {
            throw new TransactionException(ApiErrorCode.NOT_EXISTS, "User with given id dose not exists.");
        }
    }

    public List<Balance> getBalancesAsLender(Long lenderId) {
        try {
            return balanceDao.getBalancesAsLender(lenderId);
        } catch (DataIntegrityViolationException ex) {
            throw new TransactionException(ApiErrorCode.NOT_EXISTS, "User with given id dose not exists.");
        }
    }

    public List<Balance> getBalancesAsBorrower(Long borrowerId) {
        try {
            return balanceDao.getBalancesAsBorrower(borrowerId);
        } catch (DataIntegrityViolationException ex) {
            throw new TransactionException(ApiErrorCode.NOT_EXISTS, "User with given id dose not exists.");
        }
    }

    public List<GroupBalance> getBalancesBetweenUsers(List<Long> users) {
        try {
            return balanceDao.getBalancesBetweenUsers(users);
        } catch (DataIntegrityViolationException ex) {
            throw new TransactionException(ApiErrorCode.NOT_EXISTS, "User with given id dose not exists.");
        }
    }

    private TransactionEntity mapToTransactionEntity(Transaction transaction) {
        try {
            return new TransactionEntity(
                transaction.getLender(),
                transaction.getBorrower(),
                timeProvider.getCurrentTimestamp(),
                transaction.getAmount(),
                transaction.getDescription()
            );
        } catch (DataIntegrityViolationException ex) {
            throw new UserException(ApiErrorCode.NOT_EXISTS, "User with given id dose not exists.");
        }
    }

    private List<BalanceDaoImpl.BalanceDaoEntry> getBalancesFromTransactions(List<Transaction> transactions) {
        List<BalanceDaoImpl.BalanceDaoEntry> balanceEntries = new ArrayList<>();

        for (Transaction transaction : transactions) {
            Long firstUser = transaction.getBorrower();
            Long secondUser = transaction.getLender();

            //user is giving money to himself => not important for balance
            if (firstUser.equals(secondUser)) {
                continue;
            }

            BalanceDaoImpl.BalanceDaoEntry balanceEntry = firstUser < secondUser
                ? new BalanceDaoImpl.BalanceDaoEntry(firstUser, secondUser, -1 * transaction.getAmount())
                : new BalanceDaoImpl.BalanceDaoEntry(secondUser, firstUser, transaction.getAmount());
            balanceEntries.add(balanceEntry);
        }

        return balanceEntries;
    }

}
