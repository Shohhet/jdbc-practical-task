package com.shoggoth.crudapp.controller.command.impl.developer;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.DeveloperService;
import com.shoggoth.crudapp.model.service.SkillService;
import com.shoggoth.crudapp.view.UserInterface;

public class DeleteDeveloperCommand implements Command {
    private final UserInterface ui;
    private final DeveloperService service;

    public DeleteDeveloperCommand(UserInterface ui, DeveloperService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_DEVELOPER_ID_MSG);
        String id = ui.readFromConsole();
        try {
            if (service.delete(id)) {
                ui.writeToConsole(String.format(CommandUtils.DEVELOPER_DELETED_MSG, id));
            } else {
                ui.writeToConsole(String.format(CommandUtils.DEVELOPER_DOES_NOT_EXIST_MSG, id));
            }
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_DELETING_DEVELOPER_MSG, e));
        }
    }
}
