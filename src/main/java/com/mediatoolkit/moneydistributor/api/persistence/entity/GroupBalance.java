package com.mediatoolkit.moneydistributor.api.persistence.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GroupBalance {
    private final Long lender;
    private final Long borrower;
    private final Double amount;
}
