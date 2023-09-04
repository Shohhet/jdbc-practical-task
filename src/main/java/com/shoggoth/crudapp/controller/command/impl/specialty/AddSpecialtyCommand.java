package com.shoggoth.crudapp.controller.command.impl.specialty;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.SpecialtyService;
import com.shoggoth.crudapp.view.UserInterface;


public class AddSpecialtyCommand implements Command {

    private final UserInterface ui;
    private final SpecialtyService service;

    public AddSpecialtyCommand(UserInterface ui, SpecialtyService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SPECIALTY_NAME_MSG);
        String name = ui.readFromConsole();
        try {
            service.add(name).ifPresentOrElse(
                    (specialty) -> ui.writeToConsole(String.format(CommandUtils.SPECIALTY_ADDED_MSG, specialty.getName(), specialty.getId())),
                    () -> ui.writeToConsole(String.format(CommandUtils.SPECIALTY_ALREADY_EXIST_MSG, name)));

        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_ADDING_SPECIALTY_MSG, e.getMessage()));
        }
    }
}
