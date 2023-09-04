package com.shoggoth.crudapp.model.service.impl;

import com.shoggoth.crudapp.model.TransactionUtil;
import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.repository.DeveloperRepository;
import com.shoggoth.crudapp.model.repository.SpecialtyRepository;
import com.shoggoth.crudapp.model.service.SpecialtyService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;
    private final DeveloperRepository developerRepository;
    private final TransactionUtil transaction;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository, DeveloperRepository developerRepository, TransactionUtil transaction) {
        this.specialtyRepository = specialtyRepository;
        this.developerRepository = developerRepository;
        this.transaction = transaction;
    }

    @Override
    public Optional<SpecialtyEntity> add(String name) throws ServiceException {
        SpecialtyEntity SpecialtyEntity = new SpecialtyEntity();
        SpecialtyEntity.setName(name);
        Optional<SpecialtyEntity> maybeSpecialty;
        try {
            transaction.begin(specialtyRepository);
            maybeSpecialty = specialtyRepository.add(SpecialtyEntity);
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
        return maybeSpecialty;
    }

    @Override
    public Optional<SpecialtyEntity> get(String stringId) throws ServiceException {
        Optional<SpecialtyEntity> maybeSpecialty;
        Long id = Long.parseLong(stringId);
        try {
            transaction.begin(specialtyRepository);
            maybeSpecialty = specialtyRepository.getById(id);
        } catch (RepositoryException | SQLException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return maybeSpecialty;
    }

    @Override
    public Optional<SpecialtyEntity> update(String stringId, String name) throws ServiceException {
        SpecialtyEntity SpecialtyEntity = new SpecialtyEntity();
        Long id = Long.parseLong(stringId);
        SpecialtyEntity.setId(id);
        SpecialtyEntity.setName(name);
        Optional<SpecialtyEntity> maybeSpecialty;

        try {
            transaction.begin(specialtyRepository);
            maybeSpecialty = specialtyRepository.update(SpecialtyEntity);
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
        return maybeSpecialty;
    }

    @Override
    public boolean delete(String stringId) throws ServiceException {
        boolean result = false;
        Long id = Long.parseLong(stringId);
        try {
            transaction.begin(specialtyRepository,developerRepository);
            if (specialtyRepository.delete(id)) {
                developerRepository.deleteSpecialtyForDevelopers(id);
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
    public List<SpecialtyEntity> getAll() throws ServiceException {
        List<SpecialtyEntity> specialtyEntities;
        try {
            transaction.begin(specialtyRepository);
            specialtyEntities = specialtyRepository.getAll();
        } catch (RepositoryException | SQLException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return specialtyEntities;
    }
}
