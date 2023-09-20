package com.shoggoth.crudapp.model.service.validator;


import java.util.regex.Pattern;

public class SkillNameValidator implements Validator {
    private static final SkillNameValidator INSTANCE = new SkillNameValidator();

    private SkillNameValidator() {
    }

    private static final String SKILL_NAME_REGEX = "^[a-zA-Z][a-zA-Z0-9._-]{1,128}$";

    @Override
    public boolean validate(String name) {
        Pattern pattern = Pattern.compile(SKILL_NAME_REGEX);
        if (name == null || name.isEmpty()) {
            return false;
        }
        return pattern.matcher(name).matches();
    }

    public static SkillNameValidator getInstance() {
        return INSTANCE;
    }

}
