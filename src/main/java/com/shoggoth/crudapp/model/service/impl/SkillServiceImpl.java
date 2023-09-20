package com.shoggoth.crudapp.model.service.impl;

import com.shoggoth.crudapp.model.service.util.ServiceUtils;
import com.shoggoth.crudapp.model.service.util.TransactionUtil;
import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.repository.SkillRepository;
import com.shoggoth.crudapp.model.service.SkillService;
import com.shoggoth.crudapp.model.service.validator.IdValidator;
import com.shoggoth.crudapp.model.service.validator.SkillNameValidator;

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
        if (SkillNameValidator.getInstance().validate(name)) {
            skillEntity.setName(name);
        } else {
            throw new ServiceException(ServiceUtils.WRONG_SKILL_NAME_MSG);
        }
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
    public Optional<SkillEntity> get(String stringId) throws ServiceException {
        Optional<SkillEntity> maybeSkill;
        Long id;
        if (IdValidator.getInstance().validate(stringId)) {
            id = Long.parseLong(stringId);
        } else {
            throw new ServiceException(ServiceUtils.WRONG_ID_MSG);
        }
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
    public Optional<SkillEntity> update(String stringId, String name) throws ServiceException {
        SkillEntity skillEntity = new SkillEntity();
        Long id;
        if (IdValidator.getInstance().validate(stringId)) {
            id = Long.parseLong(stringId);
        } else {
            throw new ServiceException(ServiceUtils.WRONG_ID_MSG);
        }
        skillEntity.setId(id);
        if (SkillNameValidator.getInstance().validate(name)) {
            skillEntity.setName(name);
        } else {
            throw new ServiceException(ServiceUtils.WRONG_SKILL_NAME_MSG);
        }
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
    public boolean delete(String stringId) throws ServiceException {
        boolean result = false;
        Long id;
        if (IdValidator.getInstance().validate(stringId)) {
            id = Long.parseLong(stringId);
        } else {
            throw new ServiceException(ServiceUtils.WRONG_ID_MSG);
        }
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
