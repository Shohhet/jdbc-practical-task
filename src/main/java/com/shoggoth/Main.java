package com.shoggoth;

import com.shoggoth.model.ConnectionUtil;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.repository.impl.JdbcDeveloperRepositoryImpl;
import com.shoggoth.model.repository.impl.JdbcSkillRepositoryImpl;
import com.shoggoth.model.repository.impl.JdbcSpecialtyRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, RepositoryException {
        Connection connection = ConnectionUtil.getConnection();
        var jdbcDeveloperRepository = new JdbcDeveloperRepositoryImpl(connection);
        var jdbcSkillRepository = new JdbcSkillRepositoryImpl(connection);
        var jdbcSpecialtyRepository = new JdbcSpecialtyRepositoryImpl(connection);

        jdbcSkillRepository.getAll().forEach(System.out::println);
        jdbcSpecialtyRepository.getAll().forEach(System.out::println);
        jdbcDeveloperRepository.deleteSpecialtyForDevelopers(1L);
        jdbcDeveloperRepository.getAll().forEach(System.out::println);

    }
}