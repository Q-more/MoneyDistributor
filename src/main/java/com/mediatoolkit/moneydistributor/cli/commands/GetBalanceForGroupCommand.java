package com.mediatoolkit.moneydistributor.cli.commands;

import com.mediatoolkit.moneydistributor.client.RestClient;
import com.mediatoolkit.moneydistributor.api.model.BalanceGroupResponse;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public class GetBalanceForGroupCommand implements Command {
    private final RestClient restClient;

    public GetBalanceForGroupCommand(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void execute(List<String> args) {
        if (args.size() < 2) {
            System.out.println("Invalid number of arguments.");
            return;
        }
        try {
            List<BalanceGroupResponse.BalanceGroupEntry> balances = restClient.getGroupBalance(args).getBalances();
            if (balances != null) {
                balances.forEach(System.out::println);
            }
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
        }
    }

    @Override
    public String getDescription() {
        return "Returns group balance.";
    }

    @Override
    public String getExample() {
        return "GROUP_BALANCE user_id_1, user_id_2 (, user_id)+";
    }

    @Override
    public String getName() {
        return "GROUP_BALANCE";
    }
}
