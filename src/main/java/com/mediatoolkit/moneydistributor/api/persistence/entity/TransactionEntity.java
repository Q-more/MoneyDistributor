package com.mediatoolkit.moneydistributor.api.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "transaction_tbl", schema = "moneydistributor")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "from_user_id")
    private Long lender;
    @Basic
    @Column(name = "to_user_id")
    private Long borrower;
    @Basic
    @Column(name = "date")
    private Long timestamp;
    @Basic
    @Column(name = "amount")
    private Double amount;
    @Basic
    @Column(name = "description")
    private String description;

    public TransactionEntity(Long lender, Long borrower, Long timestamp, Double amount, String description) {
        this.lender = lender;
        this.borrower = borrower;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
    }
}
