package com.shoggoth.crudapp.controller.command.impl.developer;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.DeveloperService;
import com.shoggoth.crudapp.view.UserInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddDeveloperCommand implements Command {
    private final UserInterface ui;
    private final DeveloperService service;

    public AddDeveloperCommand(UserInterface ui, DeveloperService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_DEVELOPER_FIRSTNAME_MSG);
        String firstname = ui.readFromConsole();
        ui.writeToConsole(CommandUtils.ENTER_DEVELOPER_LASTNAME_MSG);
        String lastName = ui.readFromConsole();
        String skillName;
        List<String> skills = new ArrayList<>();
        do {
            ui.writeToConsole(CommandUtils.ENTER_SKILL_NAME_MSG);
            skillName = ui.readFromConsole();
            if (!skillName.isEmpty() && !skillName.isBlank()) {
                skills.add(skillName);
            }
        } while (!skillName.isEmpty() || !skillName.isBlank());
        ui.writeToConsole(CommandUtils.ENTER_SPECIALTY_NAME_MSG);
        String specialtyName = ui.readFromConsole();
        try {
            service.add(firstname, lastName, skills, specialtyName).ifPresentOrElse(
                    (developer) -> ui.writeToConsole(String.format(CommandUtils.DEVELOPER_ADDED_MSG, developer.getFirstName(), developer.getLastName(), developer.getId())),
                    () -> ui.writeToConsole(String.format(CommandUtils.DEVELOPER_ALREADY_EXIST, firstname, lastName))
            );
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_ADDING_DEVELOPER, e.getMessage()));
        }
    }
}
