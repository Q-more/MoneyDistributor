package com.mediatoolkit.moneydistributor.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long lender;
    private Long borrower;
    private Double amount;
    private String description;
}
