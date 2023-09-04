package com.shoggoth.crudapp.model.service.impl;

import com.shoggoth.crudapp.model.TransactionUtil;
import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.repository.SkillRepository;
import com.shoggoth.crudapp.model.service.SkillService;

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
    public Optional<SkillEntity> add(String name) throws ServiceException {
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setName(name);
        Optional<SkillEntity> maybeSkill;
        try {
            transaction.begin(repository);
            maybeSkill = repository.add(skillEntity);
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
    public Optional<SkillEntity> get(long id) throws ServiceException {
        Optional<SkillEntity> maybeSkill;
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
    public Optional<SkillEntity> update(long id, String name) throws ServiceException {
        SkillEntity skillEntity = new SkillEntity();
        skillEntity.setId(id);
        skillEntity.setName(name);
        Optional<SkillEntity> maybeSkill;
        try {
            transaction.begin(repository);
            maybeSkill = repository.update(skillEntity);
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
    public List<SkillEntity> getAll() throws ServiceException {
        List<SkillEntity> skillEntities;
        try {
            transaction.begin(repository);
            skillEntities = repository.getAll();
        } catch (RepositoryException | SQLException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return skillEntities;
    }
}
