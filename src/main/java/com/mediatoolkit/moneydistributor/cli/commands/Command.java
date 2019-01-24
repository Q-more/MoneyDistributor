package com.mediatoolkit.moneydistributor.cli.commands;

import java.util.List;

/**
 * Every command looks like this: COMMAND_NAME arg1,arg2,..,argN
 *
 * @author lucija
 */
public interface Command {
    void execute(List<String> args);

    String getDescription();

    String getExample();

    String getName();
}
