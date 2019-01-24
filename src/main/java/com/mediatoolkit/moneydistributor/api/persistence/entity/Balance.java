package com.mediatoolkit.moneydistributor.api.persistence.entity;

import lombok.Data;

@Data
public class Balance {
    private final UserEntity user;
    private final Double amount;
}
