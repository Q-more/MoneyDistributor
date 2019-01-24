package com.mediatoolkit.moneydistributor.cli.commands;

import com.mediatoolkit.moneydistributor.client.RestClient;
import com.mediatoolkit.moneydistributor.api.model.UserDto;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public class SaveUserCommand implements Command {

    private final RestClient client;

    public SaveUserCommand(RestClient client) {
        this.client = client;
    }

    /**
     * User:
     * first name
     * last name
     * username
     * email
     *
     * @param args
     */
    @Override
    public void execute(List<String> args) {
        if (args.size() != 4) {
            System.out.println("User need to have: first name, last name, username and email.");
            return;
        }

        try {
            Long id = client.saveUser(new UserDto(null, args.get(0), args.get(1), args.get(2), args.get(3)));
            System.out.println("User saved with id = " + id);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
        }
    }

    @Override
    public String getDescription() {
        return "Save user.";
    }

    @Override
    public String getExample() {
        return "SAVE_USER first_name,last_name, username, email";
    }

    @Override
    public String getName() {
        return "SAVE_USER";
    }
}
