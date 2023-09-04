package com.shoggoth.crudapp.controller.command.impl;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.view.UserInterface;

public class DefaultCommand implements Command {
    private static final String WRONG_COMMAND_NAME_MSG = "Wrong command name, type help to see command list.";
    private final UserInterface ui;

    public DefaultCommand(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        ui.writeToConsole(WRONG_COMMAND_NAME_MSG);
    }
}
