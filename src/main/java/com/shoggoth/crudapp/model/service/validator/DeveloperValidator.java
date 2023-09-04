package com.shoggoth.crudapp.model.service.validator;

import com.shoggoth.crudapp.model.entity.DeveloperEntity;
import com.shoggoth.crudapp.model.exceptions.ServiceException;

public class DeveloperValidator implements Validator<DeveloperEntity> {
    private static final int MAX_NAME_LENGTH = 128;

    public DeveloperEntity validate(String firstName, String lastName) throws ServiceException {
        DeveloperEntity developerEntity = new DeveloperEntity();
        if (validateName(firstName)) {
            developerEntity.setFirstName(firstName);
        } else {
            throw new ServiceException("Wrong first name format");
        }
        if (validateName(lastName)) {
            developerEntity.setFirstName(lastName);
        } else {
            throw new ServiceException("Wrong last name format");
        }
        return developerEntity;


    }

    private boolean validateName(String name) {
        return name != null && !name.isEmpty() && name.length() < MAX_NAME_LENGTH;
    }

    @Override
    public DeveloperEntity validate(String input) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
