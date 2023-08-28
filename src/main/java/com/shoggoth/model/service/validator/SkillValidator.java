package com.shoggoth.model.service.validator;

import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.exceptions.ServiceException;

public class SkillValidator implements Validator<Skill> {
    private static final SkillValidator INSTANCE = new SkillValidator();
    private SkillValidator() {}
    private static final int MAX_NAME_LENGTH = 128;

    @Override
    public Skill validate(String name) throws ServiceException {
        if (name != null && !name.isEmpty() && name.length() < MAX_NAME_LENGTH) {
            Skill skill = new Skill();
            skill.setName(name);
            return skill;
        } else {
            throw new ServiceException("Wrong skill name");
        }
    }

    public static SkillValidator getInstance() {
        return INSTANCE;
    }

}
