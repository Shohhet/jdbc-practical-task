package com.shoggoth.crudapp.controller;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.CommandContainer;
import com.shoggoth.crudapp.controller.command.CommandType;
import com.shoggoth.crudapp.controller.command.impl.ExitCommand;
import com.shoggoth.crudapp.view.UserInterface;

public class Controller {
    private static final String INPUT_COMMAND_MSG = "Input command:";
    private final UserInterface ui;
    private final CommandContainer commandContainer;

    public Controller(UserInterface ui, CommandContainer commandContainer) {
        this.ui = ui;
        this.commandContainer = commandContainer;
    }

    public void start() {
        Command command;
        do {
            ui.writeToConsole(INPUT_COMMAND_MSG);
            command = commandContainer.getCommand(ui.readFromConsole());
            if (command != null) {
                command.execute();
            }
        } while(!(command instanceof ExitCommand));
    }

}
