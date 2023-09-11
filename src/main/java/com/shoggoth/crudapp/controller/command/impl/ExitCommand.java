package com.shoggoth.crudapp.controller.command.impl;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.view.UserInterface;

public class ExitCommand implements Command {

    private final UserInterface ui;

    public ExitCommand(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.EXIT_MSG);
    }
}
