package com.shoggoth;

import com.shoggoth.model.ConnectionUtil;
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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, RepositoryException, ServiceException {
        var developerService = new DeveloperServiceImpl();
        Developer developer = new Developer(0, "Ivan", "Ivanov", null, null, null);
        Specialty specialty = new Specialty(0, "frontend", null);
        List<Skill> skills = List.of(
                new Skill(0, "Typescript", null),
                new Skill(0, "NodeJS", null),
                new Skill(0, "Docker", null));

        SkillServiceImpl skillService = new SkillServiceImpl(new JdbcSkillRepositoryImpl(), new TransactionUtil());
        skills.forEach((s) -> {
            try {
                skillService.add(s.getName()).ifPresentOrElse(System.out::println, () -> System.out.println("already exist"));
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
        });

        skillService.delete(1L);
        skillService.update(2L, "Maven");
        List<Skill> skillsFromDb = skillService.getAll();
        if (skillsFromDb != null) {
            skillsFromDb.forEach(System.out::println);
        }
        System.out.println(skillService.get(3L));
        System.out.println(skillService.get(10L));
        skillService.add("JUnit");



    }
}