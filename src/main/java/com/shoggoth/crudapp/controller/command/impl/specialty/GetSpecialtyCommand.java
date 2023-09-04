package com.shoggoth.crudapp.controller.command.impl.specialty;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.SpecialtyService;
import com.shoggoth.crudapp.view.UserInterface;

public class GetSpecialtyCommand implements Command {
    private final UserInterface ui;
    private final SpecialtyService service;

    public GetSpecialtyCommand(UserInterface ui, SpecialtyService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SPECIALTY_ID_MSG);
        String stringId = ui.readFromConsole();
        try {
            service.get(stringId).ifPresentOrElse(
                    specialty -> ui.writeToConsole(specialty.toString()),
                    () -> ui.writeToConsole(String.format(CommandUtils.SPECIALTY_DOES_NOT_EXIST_MSG, stringId))
            );
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_GETTING_SPECIALTY_MSG, e.getMessage()));
        }
    }
}
