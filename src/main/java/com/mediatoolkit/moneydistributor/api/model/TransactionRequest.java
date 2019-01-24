package com.mediatoolkit.moneydistributor.api.model;

import com.mediatoolkit.moneydistributor.api.exceptions.enums.ApiErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotNull(message = ApiErrorCode.CAN_NOT_BE_NULL)
    private Long lender;
    private String description;
    @NotNull(message = ApiErrorCode.CAN_NOT_BE_NULL)
    private List<TransactionEntry> transactions;

    @Data
    @AllArgsConstructor
    public static class TransactionEntry {
        @NotNull(message = ApiErrorCode.CAN_NOT_BE_NULL)
        private Long borrower;
        @NotNull(message = ApiErrorCode.CAN_NOT_BE_NULL)
        private Double amount;
    }
}
