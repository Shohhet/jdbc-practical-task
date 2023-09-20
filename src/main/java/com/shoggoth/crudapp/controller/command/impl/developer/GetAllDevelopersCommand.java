package com.shoggoth.crudapp.controller.command.impl.developer;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.entity.DeveloperEntity;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.DeveloperService;
import com.shoggoth.crudapp.view.UserInterface;

import java.util.List;

public class GetAllDevelopersCommand implements Command {
    private final UserInterface ui;
    private final DeveloperService service;

    public GetAllDevelopersCommand(UserInterface ui, DeveloperService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        try {
            List<DeveloperEntity> developers = service.getAll();
            if (developers.isEmpty()) {
                ui.writeToConsole(CommandUtils.EMPTY_DEVELOPER_LIST_MSG);
            } else {
                developers.forEach(skill -> ui.writeToConsole(skill.toString()));
            }
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_GETTING_DEVELOPER_MSG, e));
        }
    }
}
