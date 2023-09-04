package com.shoggoth;

import com.shoggoth.crudapp.SetupUtils;
import com.shoggoth.crudapp.controller.Controller;
import com.shoggoth.crudapp.model.TransactionUtil;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.repository.impl.JdbcDeveloperRepositoryImpl;
import com.shoggoth.crudapp.model.repository.impl.JdbcSkillRepositoryImpl;
import com.shoggoth.crudapp.model.repository.impl.JdbcSpecialtyRepositoryImpl;
import com.shoggoth.crudapp.model.service.impl.DeveloperServiceImpl;
import com.shoggoth.crudapp.model.service.impl.SkillServiceImpl;
import com.shoggoth.crudapp.model.service.impl.SpecialtyServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ServiceException, SQLException, RepositoryException {
    new SetupUtils().setup().start();

    }
}
