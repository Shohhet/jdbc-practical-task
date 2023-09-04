package com.shoggoth.crudapp.controller.command.impl;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.CommandType;
import com.shoggoth.crudapp.view.UserInterface;

import java.util.Arrays;

public class HelpCommand implements Command {
    private final UserInterface ui;

    public HelpCommand(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        for (CommandType commandType : CommandType.values()) {
            ui.writeToConsole(commandType.getName().toLowerCase());
        }
    }
}
