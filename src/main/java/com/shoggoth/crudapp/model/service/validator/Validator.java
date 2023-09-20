package com.shoggoth.crudapp.model.service.validator;

import com.shoggoth.crudapp.model.exceptions.ServiceException;

public interface Validator {
    boolean validate(String input);
}
