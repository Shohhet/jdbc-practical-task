package com.shoggoth.model.service.impl;

import com.shoggoth.model.TransactionUtil;
import com.shoggoth.model.entity.Skill;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.exceptions.ServiceException;
import com.shoggoth.model.repository.SkillRepository;
import com.shoggoth.model.service.SkillService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SkillServiceImpl implements SkillService {
    private final SkillRepository repository;
    private final TransactionUtil transaction;

    public SkillServiceImpl(SkillRepository repository, TransactionUtil transactionUtil) {
        this.repository = repository;
        this.transaction = transactionUtil;
    }


    @Override
    public Optional<Skill> add(String name) throws ServiceException {
        Skill skill = new Skill();
        skill.setName(name);
        Optional<Skill> maybeSkill;
        try {
            transaction.begin(repository);
            maybeSkill = repository.add(skill);
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
        return maybeSkill;
    }

    @Override
    public Optional<Skill> get(long id) throws ServiceException {
        Optional<Skill> maybeSkill;
        try {
            transaction.begin(repository);
            maybeSkill = repository.getById(id);
        } catch (RepositoryException | SQLException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return maybeSkill;
    }

    @Override
    public Optional<Skill> update(long id, String name) throws ServiceException {
        Skill skill = new Skill();
        skill.setId(id);
        skill.setName(name);
        Optional<Skill> maybeSkill;
        try {
            transaction.begin(repository);
            maybeSkill = repository.update(skill);
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
        return maybeSkill;
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        boolean result = false;
        try {
            transaction.begin(repository);
            if (repository.delete(id)) {
                repository.deleteForDevelopers(id);
                transaction.commit();
                result = true;
            }
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
    public List<Skill> getAll() throws ServiceException {
        List<Skill> skills;
        try {
            transaction.begin(repository);
            skills = repository.getAll();
        } catch (RepositoryException | SQLException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return skills;
    }
}
