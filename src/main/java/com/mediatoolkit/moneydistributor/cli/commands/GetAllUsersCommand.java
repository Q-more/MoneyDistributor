package com.mediatoolkit.moneydistributor.cli.commands;

import com.mediatoolkit.moneydistributor.client.RestClient;
import com.mediatoolkit.moneydistributor.api.model.UserDto;

import java.util.List;

public class GetAllUsersCommand implements Command {
    private final RestClient restClient;

    public GetAllUsersCommand(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void execute(List<String> args) {
        List<UserDto> allUsers = restClient.getAllUsers();
        allUsers.forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "Return all users.";
    }

    @Override
    public String getExample() {
        return "GET_USERS";
    }

    @Override
    public String getName() {
        return "GET_USERS";
    }
}
