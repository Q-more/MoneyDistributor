package com.mediatoolkit.moneydistributor.cli.commands;

import com.mediatoolkit.moneydistributor.client.RestClient;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public class GetUserCommand implements Command {
    private final RestClient restClient;

    public GetUserCommand(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void execute(List<String> args) {
        if (args.size() != 1) {
            System.out.println("Invalid number of arguments.");
            return;
        }
        try {
            System.out.println(restClient.getUser(args.get(0)));
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
        }

    }

    @Override
    public String getDescription() {
        return "Get user by id";
    }

    @Override
    public String getExample() {
        return "GET_USER user_id";
    }

    @Override
    public String getName() {
        return "GET_USER";
    }
}
