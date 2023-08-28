package com.shoggoth.model.service.validator;

import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.exceptions.ServiceException;

import java.util.List;
import java.util.Map;

public class DeveloperValidator implements Validator<Developer> {
    private static final int MAX_NAME_LENGTH = 128;

    public Developer validate(String firstName, String lastName) throws ServiceException {
        Developer developer = new Developer();
        if (validateName(firstName)) {
            developer.setFirstName(firstName);
        } else {
            throw new ServiceException("Wrong first name format");
        }
        if (validateName(lastName)) {
            developer.setFirstName(lastName);
        } else {
            throw new ServiceException("Wrong last name format");
        }
        return developer;


    }

    private boolean validateName(String name) {
        return name != null && !name.isEmpty() && name.length() < MAX_NAME_LENGTH;
    }

    @Override
    public Developer validate(String input) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
