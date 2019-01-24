package com.mediatoolkit.moneydistributor.cli.commands;

import java.util.List;

public class HelpCommand implements Command {

    private final List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(List<String> args) {
        commands.forEach(c -> {
            System.out.println("NAME: " + c.getName());
            System.out.println("DESCRIPTION: " + c.getDescription());
            System.out.println(c.getExample());
            System.out.println();
        });
    }

    @Override
    public String getDescription() {
        return "Get information about commands.";
    }

    @Override
    public String getExample() {
        return "HELP";
    }

    @Override
    public String getName() {
        return "HELP";
    }
}
