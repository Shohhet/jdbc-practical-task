package com.shoggoth.crudapp.controller.command.impl.skill;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.SkillService;
import com.shoggoth.crudapp.view.UserInterface;

public class UpdateSkillCommand implements Command {
    private final UserInterface ui;
    private final SkillService service;

    public UpdateSkillCommand(UserInterface ui, SkillService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SKILL_ID_MSG);
        String stringId = ui.readFromConsole();
        ui.writeToConsole(CommandUtils.ENTER_SKILL_NAME_MSG);
        String name = ui.readFromConsole();
        try {
            service.update(stringId, name).ifPresentOrElse(
                    specialty -> ui.writeToConsole(String.format(CommandUtils.SKILL_UPDATED_MSG, specialty.getId())),
                    () -> ui.writeToConsole(String.format(CommandUtils.SKILL_DOES_NOT_EXIST_MSG, stringId))
            );
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_UPDATING_SKILL_MSG, e.getMessage()));
        }
    }
}
