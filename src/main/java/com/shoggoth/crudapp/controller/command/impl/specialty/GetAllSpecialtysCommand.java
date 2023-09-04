package com.shoggoth.crudapp.controller.command.impl.specialty;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.SpecialtyService;
import com.shoggoth.crudapp.view.UserInterface;

import java.util.List;

public class GetAllSpecialtysCommand implements Command {
    private final UserInterface ui;
    private final SpecialtyService service;

    public GetAllSpecialtysCommand(UserInterface ui, SpecialtyService service) {
        this.ui = ui;
        this.service = service;
    }
    @Override
    public void execute() {
        try {
            List<SpecialtyEntity> specialtys =  service.getAll();
            if (specialtys.isEmpty()) {
                ui.writeToConsole(CommandUtils.EMPTY_SPECIALTY_LIST_MSG);
            } else {
                specialtys.forEach(specialty -> ui.writeToConsole(specialty.toString()));
            }
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_GETTING_SPECIALTY_MSG, e));
        }
    }
}
