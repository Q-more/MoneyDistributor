package com.mediatoolkit.moneydistributor.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This goes out to the world.
 *
 * @author lucija
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse {
    private List<BalanceEntry> borrowers;
    private List<BalanceEntry> lenders;

    @Data
    @AllArgsConstructor
    public static class BalanceEntry {
        private UserDto user;
        private Double amount;

        @Override
        public String toString() {
            return "(id=  " + user.getId() + ") " + user.getFirstName() + " " + user.getLastName() + " => " + amount;
        }
    }
}
