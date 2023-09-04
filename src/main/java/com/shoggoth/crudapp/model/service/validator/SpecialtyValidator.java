package com.shoggoth.crudapp.model.service.validator;

import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.exceptions.ServiceException;

public class SpecialtyValidator implements Validator<SpecialtyEntity> {
    private static final int MAX_NAME_LENGTH = 128;

    @Override
    public SpecialtyEntity validate(String name) throws ServiceException {
        if (name != null && !name.isEmpty() && name.length() < MAX_NAME_LENGTH) {
            SpecialtyEntity specialtyEntity = new SpecialtyEntity();
            specialtyEntity.setName(name);
            return specialtyEntity;
        } else {
            throw new ServiceException("Wrong specialty name");
        }
    }
}
