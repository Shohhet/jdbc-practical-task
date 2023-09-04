package com.shoggoth.crudapp.controller.command.impl;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.view.UserInterface;

public class ExitCommand implements Command {
    public static final String EXIT_MSG = "See u later.";
    private final UserInterface ui;

    public ExitCommand(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public void execute() {
        ui.writeToConsole(EXIT_MSG);
    }
}
