package com.mediatoolkit.moneydistributor.cli;

import com.mediatoolkit.moneydistributor.cli.commands.Command;
import com.mediatoolkit.moneydistributor.cli.commands.ExitCommand;
import com.mediatoolkit.moneydistributor.cli.commands.GetAllUsersCommand;
import com.mediatoolkit.moneydistributor.cli.commands.GetBalanceAsBorrowerCommand;
import com.mediatoolkit.moneydistributor.cli.commands.GetBalanceAsLenderCommand;
import com.mediatoolkit.moneydistributor.cli.commands.GetBalanceForGroupCommand;
import com.mediatoolkit.moneydistributor.cli.commands.GetUserCommand;
import com.mediatoolkit.moneydistributor.cli.commands.HelpCommand;
import com.mediatoolkit.moneydistributor.cli.commands.SaveTransactionsCommand;
import com.mediatoolkit.moneydistributor.cli.commands.SaveUserCommand;
import com.mediatoolkit.moneydistributor.client.RestClient;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoneyDistributorCLI {

    public static final String HOST = "http://localhost:8080/";
    public static final String EXIT = "EXIT";

    public static void main(String[] args) {
        // Kill spring logging (For some reason it is set to debug by default).
        LoggingSystem.get(MoneyDistributorCLI.class.getClassLoader()).setLogLevel("org.springframework", LogLevel.OFF);

        RestClient restClient = new RestClient(new RestTemplate(), HOST);
        List<Command> allCommands = Arrays.asList(
            new SaveUserCommand(restClient),
            new ExitCommand(),
            new GetAllUsersCommand(restClient),
            new GetBalanceAsBorrowerCommand(restClient),
            new GetBalanceAsLenderCommand(restClient),
            new SaveTransactionsCommand(restClient),
            new GetUserCommand(restClient),
            new GetBalanceForGroupCommand(restClient)
        );

        HelpCommand helpCommand = new HelpCommand(allCommands);

        Map<String, Command> commands = new HashMap<>();
        allCommands.forEach(c -> commands.put(c.getName(), c));
        commands.put(helpCommand.getName(), helpCommand);


        Scanner sc = new Scanner(System.in);
        String commandName = "";
        System.out.println(" > Welcome to super awesome money distributor client :) ");
        System.out.println("Commands: ");
        commands.get("HELP").execute(Collections.emptyList());
        while (!commandName.equals(EXIT)) {
            System.out.print("> ");
            String input = sc.nextLine().trim().replaceAll("\\s+", " ");

            if (input.isEmpty()) {
                continue;
            }

            String[] commandParts = input.split(" ", 2);
            commandName = commandParts[0].trim().toUpperCase();

            List<String> commandArgs = new ArrayList<>();
            Command command = commands.get(commandName);
            if (commandParts.length == 2) {

                commandArgs = Stream.of(commandParts[1].trim().split(","))
                    .map(String::trim)
                    .filter(e -> !e.isEmpty())
                    .collect(Collectors.toList());
            }

            if (command == null) {
                System.out.println("Command: " + commandName + " dose not exists. For more information run HELP.");
            } else {
                command.execute(commandArgs);
            }
        }
    }
}
