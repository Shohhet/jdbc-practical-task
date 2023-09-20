package com.shoggoth.crudapp.model.service.validator;


public class IdValidator implements Validator {
    private static final IdValidator INSTANCE = new IdValidator();
    private IdValidator() {}
    public static IdValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean validate(String stringId) {
        if (stringId != null) {
            try {
                Long.parseLong(stringId);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
