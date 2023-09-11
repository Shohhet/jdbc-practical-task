package com.shoggoth.crudapp;

import com.shoggoth.crudapp.controller.Controller;
import com.shoggoth.crudapp.controller.command.Command;
import com.shoggoth.crudapp.controller.command.CommandContainer;
import com.shoggoth.crudapp.controller.command.CommandType;
import com.shoggoth.crudapp.controller.command.impl.DefaultCommand;
import com.shoggoth.crudapp.controller.command.impl.ExitCommand;
import com.shoggoth.crudapp.controller.command.impl.HelpCommand;
import com.shoggoth.crudapp.controller.command.impl.developer.*;
import com.shoggoth.crudapp.controller.command.impl.skill.*;
import com.shoggoth.crudapp.controller.command.impl.specialty.*;
import com.shoggoth.crudapp.model.service.util.TransactionUtil;
import com.shoggoth.crudapp.model.repository.DeveloperRepository;
import com.shoggoth.crudapp.model.repository.SkillRepository;
import com.shoggoth.crudapp.model.repository.SpecialtyRepository;
import com.shoggoth.crudapp.model.repository.impl.JdbcDeveloperRepositoryImpl;
import com.shoggoth.crudapp.model.repository.impl.JdbcSkillRepositoryImpl;
import com.shoggoth.crudapp.model.repository.impl.JdbcSpecialtyRepositoryImpl;
import com.shoggoth.crudapp.model.service.DeveloperService;
import com.shoggoth.crudapp.model.service.SkillService;
import com.shoggoth.crudapp.model.service.SpecialtyService;
import com.shoggoth.crudapp.model.service.impl.DeveloperServiceImpl;
import com.shoggoth.crudapp.model.service.impl.SkillServiceImpl;
import com.shoggoth.crudapp.model.service.impl.SpecialtyServiceImpl;
import com.shoggoth.crudapp.view.UserInterface;

import java.io.InputStream;

public class SetupUtils {
    private static final InputStream is = System.in;

    public Controller setup() {
        DeveloperRepository developerRepository = new JdbcDeveloperRepositoryImpl();
        SpecialtyRepository specialtyRepository = new JdbcSpecialtyRepositoryImpl();
        SkillRepository skillRepository = new JdbcSkillRepositoryImpl();
        TransactionUtil transactionUtil = new TransactionUtil();

        DeveloperService developerService = new DeveloperServiceImpl(
                developerRepository,
                specialtyRepository,
                skillRepository,
                transactionUtil);

        SpecialtyService specialtyService = new SpecialtyServiceImpl(
                specialtyRepository,
                developerRepository,
                transactionUtil);

        SkillService skillService = new SkillServiceImpl(
                skillRepository,
                transactionUtil);

        UserInterface userInterface = new UserInterface(is);
        Command defaultCommand = new DefaultCommand(userInterface);

        CommandContainer commandContainer = new CommandContainer(defaultCommand);
        commandContainer.addCommand(CommandType.ADD_SPECIALTY.getName(), new AddSpecialtyCommand(userInterface, specialtyService));
        commandContainer.addCommand(CommandType.GET_SPECIALTY.getName(), new GetSpecialtyCommand(userInterface, specialtyService));
        commandContainer.addCommand(CommandType.GET_ALL_SPECIALTYS.getName(), new GetAllSpecialtysCommand(userInterface, specialtyService));
        commandContainer.addCommand(CommandType.UPDATE_SPECIALTY.getName(), new UpdateSpecialtyCommand(userInterface, specialtyService));
        commandContainer.addCommand(CommandType.DELETE_SPECIALTY.getName(), new DeleteSpecialtyCommand(userInterface, specialtyService));
        commandContainer.addCommand(CommandType.EXIT.getName(), new ExitCommand(userInterface));
        commandContainer.addCommand(CommandType.HELP.getName(), new HelpCommand(userInterface));
        commandContainer.addCommand(CommandType.ADD_SKILL.getName(), new AddSkillCommand(userInterface, skillService));
        commandContainer.addCommand(CommandType.DELETE_SKILL.getName(), new DeleteSkillCommand(userInterface, skillService));
        commandContainer.addCommand(CommandType.GET_ALL_SKILLS.getName(), new GetAllSkillsCommand(userInterface, skillService));
        commandContainer.addCommand(CommandType.GET_SKILL.getName(), new GetSkillCommand(userInterface, skillService));
        commandContainer.addCommand(CommandType.UPDATE_SKILL.getName(), new UpdateSkillCommand(userInterface, skillService));
        commandContainer.addCommand(CommandType.ADD_DEVELOPER.getName(), new AddDeveloperCommand(userInterface, developerService));
        commandContainer.addCommand(CommandType.GET_DEVELOPER.getName(), new GetDeveloperCommand(userInterface, developerService));
        commandContainer.addCommand(CommandType.GET_ALL_DEVELOPERS.getName(), new GetAllDevelopersCommand(userInterface, developerService));
        commandContainer.addCommand(CommandType.DELETE_DEVELOPER.getName(), new DeleteDeveloperCommand(userInterface, developerService));
        commandContainer.addCommand(CommandType.UPDATE_DEVELOPER.getName(), new UpdateDeveloperCommand(userInterface, developerService));

        return new Controller(userInterface, commandContainer);
    }
}
