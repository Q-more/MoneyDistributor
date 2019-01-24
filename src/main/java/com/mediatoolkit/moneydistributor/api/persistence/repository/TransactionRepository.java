package com.mediatoolkit.moneydistributor.api.persistence.repository;

import com.mediatoolkit.moneydistributor.api.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByLender(Long lenderId);

    List<TransactionEntity> findAllByBorrower(Long borrowerId);

    List<TransactionEntity> findAllByLenderAndBorrower(Long lenderId, Long borrowerId);
}
