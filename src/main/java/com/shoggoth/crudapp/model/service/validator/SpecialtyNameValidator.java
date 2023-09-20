package com.shoggoth.crudapp.model.service.validator;

import java.util.regex.Pattern;

public class SpecialtyNameValidator implements Validator {
    private static final SpecialtyNameValidator INSTANCE = new SpecialtyNameValidator();

    private SpecialtyNameValidator() {
    }

    private static final String SPECIALTY_NAME_REGEX = "^[a-zA-Z][a-zA-Z0-9._-]{1,128}$";

    @Override
    public boolean validate(String name) {
        Pattern pattern = Pattern.compile(SPECIALTY_NAME_REGEX);
        if (name == null || name.isEmpty()) {
            return false;
        }
        return pattern.matcher(name).matches();
    }

    public static SpecialtyNameValidator getInstance() {
        return INSTANCE;
    }
}
