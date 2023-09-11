package com.shoggoth.crudapp.controller.command.impl.skill;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.SkillService;
import com.shoggoth.crudapp.view.UserInterface;

public class GetSkillCommand implements Command {
    private final UserInterface ui;
    private final SkillService service;

    public GetSkillCommand(UserInterface ui, SkillService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        ui.writeToConsole(CommandUtils.ENTER_SKILL_ID_MSG);
        String stringId = ui.readFromConsole();
        try {
            service.get(stringId).ifPresentOrElse(
                    skill -> ui.writeToConsole(skill.toString()),
                    () -> ui.writeToConsole(String.format(CommandUtils.SKILL_DOES_NOT_EXIST_MSG, stringId))
            );
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_GETTING_SKILL_MSG, e.getMessage()));
        }
    }
}
