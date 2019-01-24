package com.mediatoolkit.moneydistributor.api.persistence.impl;

import com.mediatoolkit.moneydistributor.api.persistence.TransactionDao;
import com.mediatoolkit.moneydistributor.api.persistence.entity.TransactionEntity;
import com.mediatoolkit.moneydistributor.api.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionDaoImpl implements TransactionDao {

    private final TransactionRepository transactionRepository;

    @Override
    public void saveAll(List<TransactionEntity> transactionEntities) {
        transactionRepository.saveAll(transactionEntities);
    }

    @Override
    public TransactionEntity save(TransactionEntity transactionEntity) {
        return transactionRepository.save(transactionEntity);
    }

    @Override
    public List<TransactionEntity> getAll(Long userId) {
        return Stream.concat(
            transactionRepository.findAllByLender(userId).stream(),
            transactionRepository.findAllByBorrower(userId).stream())
            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionEntity> getAll(Long userOneId, Long userTwoId) {
        return transactionRepository.findAllByLenderAndBorrower(userOneId, userTwoId);
    }

    @Override
    public List<TransactionEntity> getAllForUser(Long id) {
        return transactionRepository.findAllByBorrower(id);
    }

    @Override
    public List<TransactionEntity> getAllFromUser(Long id) {
        return transactionRepository.findAllByLender(id);
    }
}
