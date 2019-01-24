package com.mediatoolkit.moneydistributor.api.persistence;

import com.mediatoolkit.moneydistributor.api.persistence.entity.TransactionEntity;

import java.util.List;

public interface TransactionDao {
    public void saveAll(List<TransactionEntity> transactionEntities);

    TransactionEntity save(TransactionEntity transactionEntity);

    /**
     * All users incomes and outcomes.
     *
     * @param userId user id
     *
     * @return users transactions
     */
    List<TransactionEntity> getAll(Long userId);

    /**
     * All transactions between two users
     *
     * @return transactions
     */
    List<TransactionEntity> getAll(Long userOneId, Long userTwoId);

    /**
     * All user outcomes.
     *
     * @param id users id
     *
     * @return income transactions
     */
    List<TransactionEntity> getAllForUser(Long id);

    /**
     * All user outcomes.
     *
     * @param id
     *
     * @return
     */
    List<TransactionEntity> getAllFromUser(Long id);
}
