package com.shoggoth;

import com.shoggoth.model.TransactionUtil;
import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.exceptions.ServiceException;
import com.shoggoth.model.repository.impl.JdbcDeveloperRepositoryImpl;
import com.shoggoth.model.repository.impl.JdbcSkillRepositoryImpl;
import com.shoggoth.model.repository.impl.JdbcSpecialtyRepositoryImpl;
import com.shoggoth.model.service.impl.DeveloperServiceImpl;
import com.shoggoth.model.service.impl.SkillServiceImpl;
import com.shoggoth.model.service.impl.SpecialtyServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ServiceException, SQLException, RepositoryException {
        var specialtyService = new SpecialtyServiceImpl(new JdbcSpecialtyRepositoryImpl(), new JdbcDeveloperRepositoryImpl(), new TransactionUtil());
        var skillService = new SkillServiceImpl(new JdbcSkillRepositoryImpl(), new TransactionUtil());
        var developerService = new DeveloperServiceImpl(
                new JdbcSpecialtyRepositoryImpl(),
                new JdbcDeveloperRepositoryImpl(),
                new JdbcSkillRepositoryImpl(),
                new TransactionUtil()
        );

//       developerService.add("Denis", "Petrov", List.of("1111"), null);

       developerService.get(9L).ifPresentOrElse(System.out::println, () -> System.out.println("Empty"));

    }
}