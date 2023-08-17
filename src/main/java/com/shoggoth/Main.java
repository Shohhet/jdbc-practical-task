package com.shoggoth;

import com.shoggoth.pojo.Developer;
import com.shoggoth.pojo.Skill;
import com.shoggoth.pojo.Specialty;
import com.shoggoth.repository.JdbcDeveloperRepositoryImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        var jdbcDeveloperRepository = new JdbcDeveloperRepositoryImpl(connection);
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill(1, "Java EE"));
        skills.add(new Skill(2, "Spring"));

        Developer developer = new Developer(1,
                "Ivan",
                "Ivanov",
                skills,
                new Specialty(1, "backend"));
        jdbcDeveloperRepository.add(developer);

    }
}