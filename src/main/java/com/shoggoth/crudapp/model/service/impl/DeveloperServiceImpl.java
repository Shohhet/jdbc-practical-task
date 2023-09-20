package com.shoggoth.crudapp.model.service.impl;

import com.shoggoth.crudapp.model.service.util.ServiceUtils;
import com.shoggoth.crudapp.model.service.util.TransactionUtil;
import com.shoggoth.crudapp.model.entity.DeveloperEntity;
import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.repository.DeveloperRepository;
import com.shoggoth.crudapp.model.repository.SkillRepository;
import com.shoggoth.crudapp.model.repository.SpecialtyRepository;
import com.shoggoth.crudapp.model.service.DeveloperService;
import com.shoggoth.crudapp.model.service.validator.FirstLastNameValidator;
import com.shoggoth.crudapp.model.service.validator.IdValidator;
import com.shoggoth.crudapp.model.service.validator.SkillNameValidator;
import com.shoggoth.crudapp.model.service.validator.SpecialtyNameValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DeveloperServiceImpl implements DeveloperService {

    private final SpecialtyRepository specialtyRepository;
    private final DeveloperRepository developerRepository;
    private final SkillRepository skillRepository;
    private final TransactionUtil transaction;


    public DeveloperServiceImpl(DeveloperRepository developerRepository,
                                SpecialtyRepository specialtyRepository,
                                SkillRepository skillRepository,
                                TransactionUtil transaction) {

        this.specialtyRepository = specialtyRepository;
        this.developerRepository = developerRepository;
        this.skillRepository = skillRepository;
        this.transaction = transaction;
    }


    @Override
    public Optional<DeveloperEntity> add(String firstName, String lastName, List<String> skillNames, String specialtyName) throws ServiceException {
        Optional<DeveloperEntity> maybeDeveloper;
        DeveloperEntity developer = new DeveloperEntity();
        SpecialtyEntity specialty = new SpecialtyEntity();
        SkillEntity skill = new SkillEntity();
        if (FirstLastNameValidator.getInstance().validate(firstName)) {
            developer.setFirstName(firstName);
        } else {
            throw new ServiceException(ServiceUtils.WRONG_FIRST_NAME_MSG);
        }
        if (FirstLastNameValidator.getInstance().validate(lastName)) {
            developer.setLastName(lastName);
        } else {
            throw new ServiceException(ServiceUtils.WRONG_LAST_NAME_MSG);
        }

        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            if (SpecialtyNameValidator.getInstance().validate(specialtyName)) {
                specialty.setName(specialtyName);
                var maybeSpecialty = specialtyRepository.getByName(specialty.getName());
                if (maybeSpecialty.isPresent()) {
                    specialty = maybeSpecialty.get();
                } else {
                    maybeSpecialty = specialtyRepository.add(specialty);
                    if (maybeSpecialty.isPresent()) {
                        specialty = maybeSpecialty.get();
                    }
                }
            } else {
                throw new ServiceException(ServiceUtils.WRONG_SPECIALTY_NAME_MSG);
            }
            developer.setSpecialty(specialty);

            maybeDeveloper = developerRepository.add(developer);
            if (maybeDeveloper.isPresent()) {
                developer = maybeDeveloper.get();
                developerRepository.deleteDeveloperSkills(developer.getId());

                if (skillNames != null) {
                    for (String name : skillNames) {
                        if (SkillNameValidator.getInstance().validate(name)) {
                            var maybeSkill = skillRepository.getByName(name);
                            if (maybeSkill.isPresent()) {
                                skill = maybeSkill.get();
                                developerRepository.addDeveloperSkill(developer.getId(), skill.getId());
                            } else {
                                skill.setName(name);
                                maybeSkill = skillRepository.add(skill);
                                if (maybeSkill.isPresent()) {
                                    skill = maybeSkill.get();
                                    developerRepository.addDeveloperSkill(developer.getId(), skill.getId());
                                }
                            }
                        } else {
                            throw new ServiceException(ServiceUtils.WRONG_SKILL_NAME_MSG);
                        }
                    }
                    developer.setSkills(skillRepository.getDeveloperSkills(developer.getId()));
                    maybeDeveloper = Optional.of(developer);
                }
            }
            transaction.commit();

        } catch (RepositoryException | SQLException e) {
            try {
                transaction.rollback();
            } catch (SQLException ex) {
                throw new ServiceException(ex);
            }
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return maybeDeveloper;
    }

    @Override
    public Optional<DeveloperEntity> get(String stringId) throws ServiceException {
        Optional<DeveloperEntity> maybeDeveloper;
        DeveloperEntity developer;
        Long id;
        if (IdValidator.getInstance().validate(stringId)) {
            id = Long.parseLong(stringId);
        } else {
            throw new ServiceException(ServiceUtils.WRONG_ID_MSG);
        }
        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            maybeDeveloper = developerRepository.getById(id);
            if (maybeDeveloper.isPresent()) {
                developer = maybeDeveloper.get();
                List<SkillEntity> skills = skillRepository.getDeveloperSkills(developer.getId());
                developer.setSkills(skills);
                maybeDeveloper = Optional.of(developer);
            }
        } catch (RepositoryException | SQLException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return maybeDeveloper;
    }

    @Override
    public Optional<DeveloperEntity> update(String stringId, String firstName, String lastName, List<String> skillNames, String specialtyName) throws ServiceException {
        Long developerId;
        if (IdValidator.getInstance().validate(stringId)) {
            developerId = Long.parseLong(stringId);
        } else {
            throw new ServiceException(ServiceUtils.WRONG_ID_MSG);
        }
        Optional<DeveloperEntity> maybeDeveloper;
        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            maybeDeveloper = developerRepository.getById(developerId);
            if (maybeDeveloper.isPresent()) {
                DeveloperEntity developer = maybeDeveloper.get();
                if (FirstLastNameValidator.getInstance().validate(firstName)) {
                    developer.setFirstName(firstName);
                } else {
                    throw new ServiceException(ServiceUtils.WRONG_FIRST_NAME_MSG);
                }
                if (FirstLastNameValidator.getInstance().validate(lastName)) {
                    developer.setLastName(lastName);
                } else {
                    throw new ServiceException(ServiceUtils.WRONG_LAST_NAME_MSG);
                }
                Optional<SpecialtyEntity> maybeSpecialty;
                if (SpecialtyNameValidator.getInstance().validate(specialtyName)) {
                    maybeSpecialty = specialtyRepository.getByName(specialtyName);
                } else {
                    throw new ServiceException(ServiceUtils.WRONG_SPECIALTY_NAME_MSG);
                }
                if (maybeSpecialty.isPresent()) {
                    developer.setSpecialty(maybeSpecialty.get());
                } else {
                    SpecialtyEntity specialty = new SpecialtyEntity();
                    specialty.setName(specialtyName);
                    maybeSpecialty = specialtyRepository.add(specialty);
                    maybeSpecialty.ifPresent(developer::setSpecialty);
                }
                developerRepository.deleteDeveloperSkills(developerId);
                Optional<SkillEntity> maybeSkill;
                for (String skillName : skillNames) {
                    if (SkillNameValidator.getInstance().validate(skillName)) {
                        maybeSkill = skillRepository.getByName(skillName);
                    } else {
                        throw new ServiceException(ServiceUtils.WRONG_SKILL_NAME_MSG);
                    }
                    if (maybeSkill.isPresent()) {
                        developerRepository.addDeveloperSkill(developerId, maybeSkill.get().getId());
                    } else {
                        SkillEntity skill = new SkillEntity();
                        skill.setName(skillName);
                        maybeSkill = skillRepository.add(skill);
                        if (maybeSkill.isPresent()) {
                            developerRepository.addDeveloperSkill(developerId, maybeSkill.get().getId());
                        }
                    }
                }
                maybeDeveloper = developerRepository.update(developer);
            }
            transaction.commit();
        } catch (RepositoryException | SQLException e) {
            try {
                transaction.rollback();
            } catch (SQLException ex) {
                throw new ServiceException(ex);
            }
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return maybeDeveloper;
    }

    @Override
    public boolean delete(String stringId) throws ServiceException {
        boolean result;
        Long id;
        if (IdValidator.getInstance().validate(stringId)) {
            id = Long.parseLong(stringId);
        } else {
            throw new ServiceException(ServiceUtils.WRONG_ID_MSG);
        }
        try {
            transaction.begin(developerRepository);
            result = developerRepository.delete(id);
            transaction.commit();
        } catch (RepositoryException | SQLException e) {
            try {
                transaction.rollback();
            } catch (SQLException ex) {
                throw new ServiceException(ex);
            }
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public List<DeveloperEntity> getAll() throws ServiceException {
        List<DeveloperEntity> developers;
        try {
            transaction.begin(developerRepository, skillRepository);
            developers = developerRepository.getAll();
            for (DeveloperEntity developer : developers) {
                List<SkillEntity> skills = skillRepository.getDeveloperSkills(developer.getId());
                developer.setSkills(skills);
            }
        } catch (RepositoryException | SQLException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return developers;
    }
}
