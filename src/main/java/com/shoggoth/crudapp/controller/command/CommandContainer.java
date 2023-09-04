package com.shoggoth.crudapp.controller.command;


import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private final Map<String, Command> commands;
    private final Command defaultCommand;

    public CommandContainer(Command defaultCommand) {
        this.commands = new HashMap<>();
        this.defaultCommand = defaultCommand;
    }

    public void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }
    public Command getCommand(String commandName) {
        return commands.getOrDefault(commandName, defaultCommand);
    }

}
