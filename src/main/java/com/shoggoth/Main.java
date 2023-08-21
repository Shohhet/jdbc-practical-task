package com.shoggoth;

import com.shoggoth.model.ConnectionUtil;
import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.entity.Status;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.repository.impl.JdbcDeveloperRepositoryImpl;
import com.shoggoth.model.repository.impl.JdbcSkillRepositoryImpl;
import com.shoggoth.model.repository.impl.JdbcSpecialtyRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws SQLException, RepositoryException {
        Connection connection = ConnectionUtil.getConnection();
        var jdbcDeveloperRepository = new JdbcDeveloperRepositoryImpl(connection);
        var jdbcSpecialtyRepository = new JdbcSpecialtyRepositoryImpl(connection);
        var jdbcSkillRepository = new JdbcSkillRepositoryImpl(connection);
       /* List<Skill> backendSkills = new ArrayList<>();
        List<Skill> frontendSkills = new ArrayList<>();
        List<Skill> fullstackSkills = new ArrayList<>();

        Skill javaEe = jdbcSkillRepository.add(new Skill(0, "Java EE"));
        Skill spring = jdbcSkillRepository.add(new Skill(0, "Spring"));
        Skill docker = jdbcSkillRepository.add(new Skill(0, "Docker"));
        Skill typeScript = jdbcSkillRepository.add(new Skill(0, "TypeScript"));
        Skill nodeJS = jdbcSkillRepository.add(new Skill(0, "NodeJS"));

        backendSkills.add(javaEe);
        backendSkills.add(spring);
        backendSkills.add(docker);
        frontendSkills.add(typeScript);
        frontendSkills.add(nodeJS);
        frontendSkills.add(docker);
        fullstackSkills.addAll(backendSkills);
        fullstackSkills.addAll(frontendSkills);
*/
        Optional<Specialty> specialty =  jdbcSpecialtyRepository.add(new Specialty(0, "frontend", Status.ACTIVE));
        specialty.ifPresentOrElse(System.out::println, () -> System.out.println("empty"));
   /*     jdbcSpecialtyRepository.add(new Specialty(0, "frontend", Status.ACTIVE));
        jdbcSpecialtyRepository.add(new Specialty(0, "fullstack", Status.ACTIVE));*/

        System.out.println();

      /*  Developer backendDeveloper = new Developer(0,
                "Ivan",
                "Ivanov",
                backendSkills,
                new Specialty(0L, "backend"));

        Developer frontendDeveloper = new Developer(0,
                "Petr",
                "Petrov",
                frontendSkills,
                new Specialty(0, "frontend"));
        Developer fullstackDeveloper = new Developer(0,
                "Irina",
                "Volosatova",
                fullstackSkills,
                new Specialty(0, "fullstack"));



        jdbcDeveloperRepository.add(backendDeveloper);
        jdbcDeveloperRepository.add(frontendDeveloper);
        jdbcDeveloperRepository.add(fullstackDeveloper);*/



    }
}