package com.mediatoolkit.moneydistributor.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceGroupResponse {

    private List<BalanceGroupEntry> balances;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BalanceGroupEntry implements Serializable {
        private UserDto lender;
        private UserDto borrower;
        private Double amount;

        @Override
        public String toString() {
            return lender.getFirstName() + " " + lender.getLastName() + " => " + borrower.getFirstName() + " " + borrower.getLastName() + " : " + amount;
        }
    }
}
