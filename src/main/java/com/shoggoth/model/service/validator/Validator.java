package com.shoggoth.model.service.validator;

import com.shoggoth.model.exceptions.ServiceException;

public interface Validator<T> {
    T validate(String input) throws ServiceException;
}
