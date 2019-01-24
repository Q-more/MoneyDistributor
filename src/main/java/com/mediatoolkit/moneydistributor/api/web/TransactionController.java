package com.mediatoolkit.moneydistributor.api.web;

import com.mediatoolkit.moneydistributor.api.model.Transaction;
import com.mediatoolkit.moneydistributor.api.model.TransactionRequest;
import com.mediatoolkit.moneydistributor.api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionController {
    private static final Logger LOG = LoggerFactory.getLogger(BalanceController.class);

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> saveTransactions(@RequestBody @Valid TransactionRequest transactionRequest) {
        LOG.info("Saving transactions. Lender =  " + transactionRequest.getLender());
        String description = transactionRequest.getDescription();
        Long lender = transactionRequest.getLender();

        transactionService.saveAll(
            transactionRequest.getTransactions().stream()
                .map(e -> new Transaction(lender, e.getBorrower(), e.getAmount(), description))
                .collect(Collectors.toList())
        );

        return ResponseEntity.noContent().build();
    }

}
