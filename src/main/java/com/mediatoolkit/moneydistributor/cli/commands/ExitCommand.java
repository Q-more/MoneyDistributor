package com.mediatoolkit.moneydistributor.cli.commands;

import java.util.List;

public class ExitCommand implements Command {
    @Override
    public void execute(List<String> args) {
        System.out.println("Exiting...");
    }

    @Override
    public String getDescription() {
        return "Exit money distributor client.";
    }

    @Override
    public String getExample() {
        return "EXIT ";
    }

    @Override
    public String getName() {
        return "EXIT";
    }
}
