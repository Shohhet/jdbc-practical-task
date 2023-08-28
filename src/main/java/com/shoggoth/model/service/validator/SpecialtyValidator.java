package com.shoggoth.model.service.validator;

import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.exceptions.ServiceException;

public class SpecialtyValidator implements Validator<Specialty> {
    private static final int MAX_NAME_LENGTH = 128;

    @Override
    public Specialty validate(String name) throws ServiceException {
        if (name != null && !name.isEmpty() && name.length() < MAX_NAME_LENGTH) {
            Specialty specialty = new Specialty();
            specialty.setName(name);
            return specialty;
        } else {
            throw new ServiceException("Wrong specialty name");
        }
    }
}
