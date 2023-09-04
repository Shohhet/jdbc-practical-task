package com.shoggoth.crudapp.model.service.impl;

import com.shoggoth.crudapp.model.TransactionUtil;
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
        DeveloperEntity developerEntity = new DeveloperEntity();
        SpecialtyEntity specialtyEntity = new SpecialtyEntity();
        SkillEntity skillEntity = new SkillEntity();
        developerEntity.setFirstName(firstName);
        developerEntity.setLastName(lastName);
        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            if (specialtyName != null && !specialtyName.isEmpty()) {
                specialtyEntity.setName(specialtyName);
                var maybeSpecialty = specialtyRepository.getByName(specialtyEntity);
                if (maybeSpecialty.isPresent()) {
                    specialtyEntity = maybeSpecialty.get();
                } else {
                    maybeSpecialty = specialtyRepository.add(specialtyEntity);
                    if (maybeSpecialty.isPresent()) {
                        specialtyEntity = maybeSpecialty.get();
                    }
                }
            }
            developerEntity.setSpecialty(specialtyEntity);
            developerEntity = developerRepository.add(developerEntity).orElseThrow(() -> new ServiceException("Can't add developer."));

            if (skillNames != null) {
                for (String name : skillNames) {
                    skillEntity.setName(name);
                    var maybeSkill = skillRepository.getByName(skillEntity);
                    if (maybeSkill.isPresent()) {
                        skillEntity = maybeSkill.get();
                        developerRepository.addDeveloperSkill(developerEntity.getId(), skillEntity.getId());
                    } else {
                        maybeSkill = skillRepository.add(skillEntity);
                        if (maybeSkill.isPresent()) {
                            skillEntity = maybeSkill.get();
                            developerRepository.addDeveloperSkill(developerEntity.getId(), skillEntity.getId());
                        }
                    }
                }
                developerEntity.setSkills(skillRepository.getDeveloperSkills(developerEntity.getId()));
                maybeDeveloper = Optional.of(developerEntity);
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
    public Optional<DeveloperEntity> get(Long id) throws ServiceException {
        Optional<DeveloperEntity> maybeDeveloper;
        DeveloperEntity developerEntity;
        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            maybeDeveloper = developerRepository.getById(id);
            if (maybeDeveloper.isPresent()) {
                developerEntity = maybeDeveloper.get();
                List<SkillEntity> skillEntities = skillRepository.getDeveloperSkills(developerEntity.getId());
                developerEntity.setSkills(skillEntities);
                maybeDeveloper = Optional.of(developerEntity);
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
    public Optional<DeveloperEntity> update(Long id, String firstName, String lastName, List<String> skillNames, String specialtyName) throws ServiceException {

        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            var maybeDeveloper = developerRepository.getById(id);

            if (maybeDeveloper.isPresent()) {
                var developer = maybeDeveloper.get();
                SpecialtyEntity updatedSpecialtyEntity = new SpecialtyEntity();
                updatedSpecialtyEntity.setId(developer.getSpecialty().getId());
                updatedSpecialtyEntity.setName(specialtyName);
                specialtyRepository.update(updatedSpecialtyEntity).ifPresent(developer::setSpecialty);


            }

        } catch (SQLException | RepositoryException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        return false;
    }

    @Override
    public List<DeveloperEntity> getAll() throws ServiceException {
        return null;
    }
}
