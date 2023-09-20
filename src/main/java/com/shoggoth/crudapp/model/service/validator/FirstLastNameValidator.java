package com.shoggoth.crudapp.model.service.validator;


import java.util.regex.Pattern;

public class FirstLastNameValidator implements Validator {
    private static final String NAME_REGEX = "^[\\p{L}][\\p{L}`-]?[\\p{L}`]{1,128}$";
    private static final FirstLastNameValidator INSTANCE = new FirstLastNameValidator();

    private FirstLastNameValidator() {
    }

    public static FirstLastNameValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean validate(String name){
        Pattern pattern = Pattern.compile(NAME_REGEX);
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return pattern.matcher(name).matches();
    }
}
