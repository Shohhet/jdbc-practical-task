package com.shoggoth.crudapp.controller.command.impl.specialty;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.CommandContainer;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.SpecialtyService;
import com.shoggoth.crudapp.view.UserInterface;

public class DeleteSpecialtyCommand implements Command {

    private final UserInterface ui;
    private final SpecialtyService service;

    public DeleteSpecialtyCommand(UserInterface ui, SpecialtyService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SPECIALTY_ID_MSG);
        String id = ui.readFromConsole();
        try {
            if (service.delete(id)) {
                ui.writeToConsole(String.format(CommandUtils.SPECIALTY_DELETED_MSG, id));
            } else {
                ui.writeToConsole(String.format(CommandUtils.SPECIALTY_DOES_NOT_EXIST_MSG, id));
            }
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_DELETING_SPECIALTY_MSG, e));
        }

    }
}
