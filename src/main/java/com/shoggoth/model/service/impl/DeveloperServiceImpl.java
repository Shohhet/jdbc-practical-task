package com.shoggoth.model.service.impl;

import com.shoggoth.model.ConnectionUtil;
import com.shoggoth.model.entity.Developer;
import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.exceptions.ServiceException;
import com.shoggoth.model.repository.impl.JdbcDeveloperRepositoryImpl;
import com.shoggoth.model.repository.impl.JdbcSkillRepositoryImpl;
import com.shoggoth.model.repository.impl.JdbcSpecialtyRepositoryImpl;
import com.shoggoth.model.service.DeveloperService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DeveloperServiceImpl implements DeveloperService {

    @Override
    public Optional<Developer> add(Developer developer, List<Skill> skills, Specialty specialty) throws ServiceException {

        try(var connection = ConnectionUtil.getConnection())  {
            var jdbcSkillRepository = new JdbcSkillRepositoryImpl(connection);
            var jdbcSpecialtyRepository = new JdbcSpecialtyRepositoryImpl(connection);
            var jdbcDeveloperRepository = new JdbcDeveloperRepositoryImpl(connection);
            Optional<Developer> maybeDeveloper = jdbcDeveloperRepository.add(developer);
            Optional<Specialty> maybeSpecialty = jdbcSpecialtyRepository.add(specialty);

            if (maybeDeveloper.isPresent()) {
                Long developerId = maybeDeveloper.get().getId();
                if (maybeSpecialty.isPresent()) {
                    Long specialtyId = maybeSpecialty.get().getId();
                    jdbcDeveloperRepository.setSpecialtyId(developerId, specialtyId);
                }
                for (Skill skill : skills) {
                    Optional<Skill> maybeSkill = jdbcSkillRepository.add(skill);
                    if (maybeSkill.isPresent()) {
                        Long skillId = maybeSkill.get().getId();
                        jdbcDeveloperRepository.addDeveloperSkill(developerId, skillId);
                    }
                }
            }
        } catch (RepositoryException | SQLException e) {
            throw new ServiceException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Developer> get(Long id) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Optional<Developer> update(Developer developer, List<Skill> skills, Specialty specialty) throws ServiceException {
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
