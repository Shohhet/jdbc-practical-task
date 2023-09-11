package com.shoggoth.crudapp.controller.command.impl.developer;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.DeveloperService;
import com.shoggoth.crudapp.view.UserInterface;

public class GetDeveloperCommand implements Command {


    private final UserInterface ui;
    private final DeveloperService service;

    public GetDeveloperCommand(UserInterface ui, DeveloperService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_DEVELOPER_ID_MSG);
        String stringId = ui.readFromConsole();
        try {
            service.get(stringId).ifPresentOrElse(
                    developer -> ui.writeToConsole(developer.toString()),
                    () -> ui.writeToConsole(String.format(CommandUtils.DEVELOPER_DOES_NOT_EXIST_MSG, stringId))
            );
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_GETTING_DEVELOPER_MSG, e.getMessage()));
        }
    }
}

