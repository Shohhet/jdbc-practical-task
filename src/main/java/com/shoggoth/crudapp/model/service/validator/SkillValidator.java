package com.shoggoth.crudapp.model.service.validator;

import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.exceptions.ServiceException;

public class SkillValidator implements Validator<SkillEntity> {
    private static final SkillValidator INSTANCE = new SkillValidator();
    private SkillValidator() {}
    private static final int MAX_NAME_LENGTH = 128;

    @Override
    public SkillEntity validate(String name) throws ServiceException {
        if (name != null && !name.isEmpty() && name.length() < MAX_NAME_LENGTH) {
            SkillEntity skillEntity = new SkillEntity();
            skillEntity.setName(name);
            return skillEntity;
        } else {
            throw new ServiceException("Wrong skill name");
        }
    }

    public static SkillValidator getInstance() {
        return INSTANCE;
    }

}
