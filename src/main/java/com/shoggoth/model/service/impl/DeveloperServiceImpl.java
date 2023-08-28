package com.shoggoth.model.service.impl;

import com.shoggoth.model.TransactionUtil;
import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.exceptions.ServiceException;
import com.shoggoth.model.repository.DeveloperRepository;
import com.shoggoth.model.repository.SkillRepository;
import com.shoggoth.model.repository.SpecialtyRepository;
import com.shoggoth.model.service.DeveloperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DeveloperServiceImpl implements DeveloperService {
    private final SpecialtyRepository specialtyRepository;
    private final DeveloperRepository developerRepository;
    private final SkillRepository skillRepository;
    private final TransactionUtil transaction;


    public DeveloperServiceImpl(SpecialtyRepository specialtyRepository,
                                DeveloperRepository developerRepository,
                                SkillRepository skillRepository,
                                TransactionUtil transaction) {

        this.specialtyRepository = specialtyRepository;
        this.developerRepository = developerRepository;
        this.skillRepository = skillRepository;
        this.transaction = transaction;
    }


    @Override
    public Optional<Developer> add(String firstName, String lastName, List<String> skillNames, String specialtyName) throws ServiceException {
        Optional<Developer> maybeDeveloper = Optional.empty();
        Developer developer = new Developer();
        Specialty specialty = new Specialty();
        Skill skill = new Skill();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            if (specialtyName != null && !specialtyName.isEmpty()) {
                specialty.setName(specialtyName);
                var maybeSpecialty = specialtyRepository.getByName(specialty);
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
            developer = developerRepository.add(developer).orElseThrow(() -> new ServiceException("Can't add developer."));

            if (skillNames != null) {
                for (String name : skillNames) {
                    skill.setName(name);
                    var maybeSkill = skillRepository.getByName(skill);
                    if (maybeSkill.isPresent()) {
                        skill = maybeSkill.get();
                        developerRepository.addDeveloperSkill(developer.getId(), skill.getId());
                    } else {
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
    public Optional<Developer> get(Long id) throws ServiceException {
        Optional<Developer> maybeDeveloper;
        Developer developer;
        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            maybeDeveloper = developerRepository.getById(id);
            if (maybeDeveloper.isPresent()) {
                developer = maybeDeveloper.get();
                List<Skill> skills = skillRepository.getDeveloperSkills(developer.getId());
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
    public Optional<Developer> update(Long id, String firstName, String lastName, List<String> skillNames, String specialtyName) throws ServiceException {

        try {
            transaction.begin(developerRepository, specialtyRepository, skillRepository);
            var maybeDeveloper = developerRepository.getById(id);

            if (maybeDeveloper.isPresent()) {
                var developer = maybeDeveloper.get();
                Specialty updatedSpecialty = new Specialty();
                updatedSpecialty.setId(developer.getSpecialty().getId());
                updatedSpecialty.setName(specialtyName);
                specialtyRepository.update(updatedSpecialty).ifPresent(developer::setSpecialty);


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
    public List<Developer> getAll() throws ServiceException {
        return null;
    }
}
