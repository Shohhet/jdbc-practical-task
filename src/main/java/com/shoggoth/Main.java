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
    public static void main(String[] args) throws SQLException, RepositoryException, ServiceException {
        Developer developer = new Developer(0, "Ivan", "Ivanov", null, null, null);
        Specialty specialty = new Specialty(0, "frontend", null);
        var specialtyService = new SpecialtyServiceImpl(new JdbcSpecialtyRepositoryImpl(), new JdbcDeveloperRepositoryImpl(), new TransactionUtil());
        specialtyService.getAll().forEach(System.out::println);
        specialtyService.update(1L, "frontend");
        specialtyService.get(1L).ifPresent(System.out::println);


    }
}