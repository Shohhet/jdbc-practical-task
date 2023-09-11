package com.shoggoth.crudapp.controller.command.impl.skill;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.SkillService;
import com.shoggoth.crudapp.view.UserInterface;

public class AddSkillCommand implements Command {
    private final UserInterface ui;
    private final SkillService service;

    public AddSkillCommand(UserInterface ui, SkillService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SKILL_NAME_MSG);
        String name = ui.readFromConsole();
        try {
            service.add(name).ifPresentOrElse(
                    (skill) -> ui.writeToConsole(String.format(CommandUtils.SKILL_ADDED_MSG, skill.getName(), skill.getId())),
                    () -> ui.writeToConsole(String.format(CommandUtils.SKILL_ALREADY_EXIST, name))
            );
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_ADDING_SKILL_MSG, e));
        }
    }
}
