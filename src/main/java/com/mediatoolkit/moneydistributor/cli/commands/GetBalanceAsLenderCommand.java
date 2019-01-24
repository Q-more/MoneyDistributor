package com.mediatoolkit.moneydistributor.cli.commands;

import com.mediatoolkit.moneydistributor.client.RestClient;
import com.mediatoolkit.moneydistributor.api.model.BalanceResponse;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public class GetBalanceAsLenderCommand implements Command {
    private final RestClient restClient;

    public GetBalanceAsLenderCommand(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void execute(List<String> args) {
        if (args.size() != 1) {
            System.out.println("Invalid number of arguments.");
            return;
        }

        try {
            List<BalanceResponse.BalanceEntry> borrowers = restClient.getBalancesAsLander(args.get(0)).getBorrowers();
            if (borrowers != null) {
                borrowers.forEach(System.out::println);
            }
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
        }
    }

    @Override
    public String getDescription() {
        return "Return all borrowers of given user.";
    }

    @Override
    public String getExample() {
        return "WHO_OWE_ME user_id";
    }

    @Override
    public String getName() {
        return "WHO_OWE_ME";
    }
}
