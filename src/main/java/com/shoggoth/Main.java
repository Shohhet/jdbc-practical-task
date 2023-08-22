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
        Specialty specialty = new Specialty();
        Status status;
        Developer developer = new Developer(0, "Ivan", "Petrov",null, null, null);
        jdbcDeveloperRepository.add(developer).ifPresentOrElse(System.out::println, ()-> System.out.println("empty"));

    }
}