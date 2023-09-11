package com.shoggoth.crudapp.controller.command.impl.skill;

import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.impl.CommandUtils;
import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.service.SkillService;
import com.shoggoth.crudapp.view.UserInterface;

import java.util.List;

public class GetAllSkillsCommand implements Command {
    private final UserInterface ui;
    private final SkillService service;

    public GetAllSkillsCommand(UserInterface ui, SkillService service) {
        this.ui = ui;
        this.service = service;
    }

    @Override
    public void execute() {
        try {
            List<SkillEntity> skills = service.getAll();
            if (skills.isEmpty()) {
                ui.writeToConsole(CommandUtils.EMPTY_SKILL_LIST_MSG);
            } else {
                skills.forEach(skill -> ui.writeToConsole(skill.toString()));
            }
        } catch (ServiceException e) {
            ui.writeToConsole(String.format(CommandUtils.ERROR_GETTING_SKILL_MSG, e));
        }
    }
}
