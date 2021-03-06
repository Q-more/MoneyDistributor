package com.mediatoolkit.moneydistributor.cli.commands;

import com.mediatoolkit.moneydistributor.client.RestClient;
import com.mediatoolkit.moneydistributor.api.model.BalanceResponse;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public class GetBalanceAsBorrowerCommand implements Command {

    private final RestClient restClient;

    public GetBalanceAsBorrowerCommand(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void execute(List<String> args) {
        if (args.size() != 1) {
            System.out.println("Invalid number of arguments.");
            return;
        }

        try {
            List<BalanceResponse.BalanceEntry> lenders = restClient.getBalancesAsBorrower(args.get(0)).getLenders();
            if (lenders != null) {
                lenders.forEach(System.out::println);
            }
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
        }

    }

    @Override
    public String getDescription() {
        return "Returns all lenders of given user.";
    }

    @Override
    public String getExample() {
        return "TO_WHOM_I_OWE user_id";
    }

    @Override
    public String getName() {
        return "TO_WHOM_I_OWE";
    }
}
