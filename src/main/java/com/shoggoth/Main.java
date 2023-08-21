package com.shoggoth;

import com.shoggoth.entity.Developer;
import com.shoggoth.entity.Skill;
import com.shoggoth.entity.Specialty;
import com.shoggoth.repository.impl.JdbcDeveloperRepositoryImpl;
import com.shoggoth.repository.impl.JdbcSkillRepositoryImpl;
import com.shoggoth.repository.impl.JdbcSpecialtyRepositoryImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        var jdbcDeveloperRepository = new JdbcDeveloperRepositoryImpl(connection);
        var jdbcSpecialtyRepository = new JdbcSpecialtyRepositoryImpl(connection);
        var jdbcSkillRepository = new JdbcSkillRepositoryImpl(connection);
        List<Skill> backendSkills = new ArrayList<>();
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

/*        jdbcSpecialtyRepository.add(new Specialty(0, "backend"));
        jdbcSpecialtyRepository.add(new Specialty(0, "frontend"));
        jdbcSpecialtyRepository.add(new Specialty(0, "fullstack"));*/

        System.out.println();

        Developer backendDeveloper = new Developer(0,
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
        jdbcDeveloperRepository.add(fullstackDeveloper);



    }
}