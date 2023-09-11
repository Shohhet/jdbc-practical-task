package com.shoggoth.crudapp.model.service.impl;

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
        Optional<DeveloperEntity> maybeDeveloper = Optional.empty();
        DeveloperEntity developer = new DeveloperEntity();
        SpecialtyEntity specialty = new SpecialtyEntity();
        SkillEntity skill = new SkillEntity();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            if (specialtyName != null && !specialtyName.isEmpty()) {
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
            }
            developer.setSpecialty(specialty);
            developer = developerRepository.add(developer).orElseThrow(() -> new ServiceException("Can't add developer.")); //TODO remove exception?

            if (skillNames != null) {
                for (String name : skillNames) {
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
                }
                developer.setSkills(skillRepository.getDeveloperSkills(developer.getId()));
                maybeDeveloper = Optional.of(developer);
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
        Long id = Long.parseLong(stringId);
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
        Long developerId = Long.parseLong(stringId);
        Optional<DeveloperEntity> maybeDeveloper = Optional.empty();
        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            maybeDeveloper = developerRepository.getById(developerId);
            if (maybeDeveloper.isPresent()) {
                DeveloperEntity developer = maybeDeveloper.get();
                developer.setFirstName(firstName);
                developer.setLastName(lastName);
                Optional<SpecialtyEntity> maybeSpecialty;
                maybeSpecialty = specialtyRepository.getByName(specialtyName);
                if (maybeSpecialty.isPresent()) {
                    developer.setSpecialty(maybeSpecialty.get());
                } else {
                    SpecialtyEntity specialty = new SpecialtyEntity();
                    specialty.setName(specialtyName);
                    maybeSpecialty = specialtyRepository.add(specialty);
                    maybeSpecialty.ifPresent(developer::setSpecialty);
                }
                developerRepository.deleteDeveloperSkills(developerId);
                for(String skillName : skillNames) {
                    Optional<SkillEntity> maybeSkill = skillRepository.getByName(skillName);
                    if (maybeSkill.isPresent()) {
                        developerRepository.addDeveloperSkill(developerId, maybeSkill.get().getId());
                    } else {
                        SkillEntity skill = new SkillEntity();
                        skill.setName(skillName);
                        maybeSkill = skillRepository.add(skill);
                        if(maybeSkill.isPresent()) {
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
        Long id = Long.parseLong(stringId);
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
