package com.mediatoolkit.moneydistributor.api.web;

import com.mediatoolkit.moneydistributor.api.model.BalanceGroupResponse;
import com.mediatoolkit.moneydistributor.api.model.BalanceResponse;
import com.mediatoolkit.moneydistributor.api.model.UserDto;
import com.mediatoolkit.moneydistributor.api.persistence.entity.UserEntity;
import com.mediatoolkit.moneydistributor.api.service.TransactionService;
import com.mediatoolkit.moneydistributor.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/balances")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BalanceController {
    private static final Logger LOG = LoggerFactory.getLogger(BalanceController.class);

    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping(path = "/lender/{lenderId}")
    public ResponseEntity<BalanceResponse> getBalanceAsLander(@PathVariable Long lenderId) {
        LOG.info("Getting borrowers for user with id " + lenderId);
        return ResponseEntity.ok(new BalanceResponse(getBorrowers(lenderId), null));
    }

    @GetMapping(path = "/borrower/{borrowerId}")
    public ResponseEntity<BalanceResponse> getBalanceAsBorrower(@PathVariable Long borrowerId) {
        LOG.info("Getting lenders for user with id " + borrowerId);
        return ResponseEntity.ok(new BalanceResponse(null, getLenders(borrowerId)));
    }

    @GetMapping(path = "/all/{userId}")
    public ResponseEntity<BalanceResponse> getAllBalances(@PathVariable Long userId) {
        LOG.info("Getting all balances for user with id " + userId);
        return ResponseEntity.ok(new BalanceResponse(getBorrowers(userId), getLenders(userId)));
    }

    @GetMapping(path = "/group")
    public ResponseEntity<BalanceGroupResponse> getGroupBalance(@RequestParam List<Long> users) {
        LOG.info("Getting balance for group= " + users);
        List<BalanceGroupResponse.BalanceGroupEntry> entries = transactionService.getBalancesBetweenUsers(users).stream()
            .map(b -> new BalanceGroupResponse.BalanceGroupEntry(
                userService.getUser(b.getLender()),
                userService.getUser(b.getBorrower()),
                b.getAmount()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(new BalanceGroupResponse(entries));
    }

    private List<BalanceResponse.BalanceEntry> getBorrowers(Long lenderId) {
        return transactionService.getBalancesAsLender(lenderId).stream()
            .map(b -> {
                UserEntity user = b.getUser();
                return new BalanceResponse.BalanceEntry(
                    new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail()),
                    b.getAmount()
                );
            })
            .collect(Collectors.toList());
    }

    private List<BalanceResponse.BalanceEntry> getLenders(Long borrowerId) {
        return transactionService.getBalancesAsBorrower(borrowerId).stream()
            .map(b -> {
                UserEntity user = b.getUser();
                return new BalanceResponse.BalanceEntry(
                    new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail()),
                    b.getAmount()
                );
            })
            .collect(Collectors.toList());
    }
}
