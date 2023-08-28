package com.shoggoth.model.service.impl;

import com.shoggoth.model.TransactionUtil;
import com.shoggoth.model.entity.Specialty;
import com.shoggoth.model.exceptions.RepositoryException;
import com.shoggoth.model.exceptions.ServiceException;
import com.shoggoth.model.repository.DeveloperRepository;
import com.shoggoth.model.repository.SpecialtyRepository;
import com.shoggoth.model.service.SpecialtyService;

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
    public Optional<Specialty> add(String name) throws ServiceException {
        Specialty Specialty = new Specialty();
        Specialty.setName(name);
        Optional<Specialty> maybeSpecialty;
        try {
            transaction.begin(specialtyRepository);
            maybeSpecialty = specialtyRepository.add(Specialty);
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
    public Optional<Specialty> get(Long id) throws ServiceException {
        Optional<Specialty> maybeSpecialty;
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
    public Optional<Specialty> update(Long id, String name) throws ServiceException {
        Specialty Specialty = new Specialty();
        Specialty.setId(id);
        Specialty.setName(name);
        Optional<Specialty> maybeSpecialty;
        try {
            transaction.begin(specialtyRepository);
            maybeSpecialty = specialtyRepository.update(Specialty);
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
    public boolean delete(Long id) throws ServiceException {
        boolean result = false;
        try {
            transaction.begin(specialtyRepository,developerRepository);
            if (specialtyRepository.delete(id) &&
                developerRepository.deleteSpecialtyForDevelopers(id)) {
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
    public List<Specialty> getAll() throws ServiceException {
        List<Specialty> Specialtys;
        try {
            transaction.begin(specialtyRepository);
            Specialtys = specialtyRepository.getAll();
        } catch (RepositoryException | SQLException e) {
            throw new ServiceException(e);
        } finally {
            try {
                transaction.end();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
        return Specialtys;
    }
}
