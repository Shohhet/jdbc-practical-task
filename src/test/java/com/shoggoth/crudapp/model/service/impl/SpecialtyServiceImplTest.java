package com.shoggoth.crudapp.model.service.impl;

import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.entity.Status;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.repository.DeveloperRepository;
import com.shoggoth.crudapp.model.repository.GenericRepository;
import com.shoggoth.crudapp.model.repository.SpecialtyRepository;
import com.shoggoth.crudapp.model.service.SpecialtyService;
import com.shoggoth.crudapp.model.service.util.ServiceUtils;
import com.shoggoth.crudapp.model.service.util.TransactionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyServiceImplTest {
    static final String specialtyName = "backend";
    static final String notValidSpecialtyName = "2424eszfsz";
    static final String stringId = "1";
    static final String notValidStringId = "1ef";
    static final Long id = 1L;
    SpecialtyRepository specialtyRepository;
    DeveloperRepository developerRepository;
    TransactionUtil transactionUtil;
    SpecialtyService specialtyService;
    SpecialtyEntity specialty;



    @BeforeEach
    public void initService() throws SQLException {
        specialtyRepository = Mockito.mock(SpecialtyRepository.class);
        developerRepository = Mockito.mock(DeveloperRepository.class);
        transactionUtil = Mockito.mock(TransactionUtil.class);
        specialtyService = new SpecialtyServiceImpl(specialtyRepository, developerRepository, transactionUtil);
        ArgumentCaptor<GenericRepository> valueCapture = ArgumentCaptor.forClass(GenericRepository.class);
        Mockito.doNothing().when(transactionUtil).begin(valueCapture.capture());
        Mockito.doNothing().when(transactionUtil).end();
        Mockito.doNothing().when(transactionUtil).commit();
        Mockito.doNothing().when(transactionUtil).rollback();

        specialty = new SpecialtyEntity(id, specialtyName, Status.ACTIVE);

    }

    @Test
    public void whenAddingSpecialtyDoesNotExistThenReturnOptionalOfSpecialty() throws RepositoryException, ServiceException {

        Mockito.when(specialtyRepository.add(Mockito.any(SpecialtyEntity.class))).thenReturn(Optional.of(specialty));
        assertEquals(specialty, specialtyService.add(specialtyName).get());
    }

    @Test
    public void whenAddingSpecialtyAlreadyExistThenReturnOptionalOfEmpty() throws RepositoryException, ServiceException {
        Mockito.when(specialtyRepository.add(Mockito.any(SpecialtyEntity.class))).thenReturn(Optional.empty());
        assertTrue(specialtyService.add(specialtyName).isEmpty());
    }

    @Test
    public void whenAddingSpecialtyNameNotValidThenThrowServiceException() {
        assertThrows(
                ServiceException.class,
                () -> specialtyService.add(notValidSpecialtyName),
                ServiceUtils.WRONG_SPECIALTY_NAME_MSG);
    }

    @Test
    public void whenUpdatingSpecialtyExistThenReturnOptionalOfUpdatedSpecialty() throws RepositoryException, ServiceException {
        Mockito.when(specialtyRepository.update(Mockito.any(SpecialtyEntity.class))).thenReturn(Optional.of(specialty));
        assertEquals(specialty, specialtyService.update(stringId, specialtyName).get());
    }

    @Test
    public void whenUpdatingSpecialtyDoesNotExistThenReturnOptionalOfEmpty() throws RepositoryException, ServiceException {
        Mockito.when(specialtyRepository.update(Mockito.any(SpecialtyEntity.class))).thenReturn(Optional.empty());
        assertTrue(specialtyService.update(stringId, specialtyName).isEmpty());
    }

    @Test
    public void whenUpdatingSpecialtyNameNotValidThenThrowServiceException() {
        assertThrows(
                ServiceException.class,
                () -> specialtyService.update(stringId, notValidSpecialtyName),
                ServiceUtils.WRONG_SPECIALTY_NAME_MSG);
    }

    @Test
    public void whenUpdatingSpecialtyIdNotValidThenThrowServiceException() {
        assertThrows(
                ServiceException.class,
                () -> specialtyService.update(notValidStringId, specialtyName),
                ServiceUtils.WRONG_ID_MSG);
    }

    @Test
    public void whenDeletingSpecialtyExistThenReturnTrue() throws ServiceException, RepositoryException {
        Mockito.when(specialtyRepository.delete(id)).thenReturn(true);
        assertTrue(specialtyService.delete(stringId));
    }

    @Test
    public void whenDeletingSpecialtyDoesNotExistThenReturnFalse() throws RepositoryException, ServiceException {
        Mockito.when(specialtyRepository.delete(id)).thenReturn(false);
        assertFalse(specialtyService.delete(stringId));
    }
    @Test
    public void whenDeletingSpecialtyIdNotValidThenThrowServiceException() {
        assertThrows(ServiceException.class,
                () -> specialtyService.delete(notValidStringId),
                ServiceUtils.WRONG_ID_MSG);
    }

    @Test
    public void whenGettingSpecialtyExistThenReturnOptionalOfSpecialty() throws ServiceException, RepositoryException {
        Mockito.when(specialtyRepository.getById(id)).thenReturn(Optional.of(specialty));
        assertEquals(specialty,specialtyService.get(stringId).get());
    }

    @Test
    public void whenGettingSpecialtyDoesNotExistThenReturnOptionalOfEmpty() throws RepositoryException, ServiceException {
        Mockito.when(specialtyRepository.getById(id)).thenReturn(Optional.empty());
        assertTrue(specialtyService.get(stringId).isEmpty());
    }
    @Test
    public void whenGettingSpecialtyIdNotValidThenThrowServiceException() {
        assertThrows(ServiceException.class,
                () -> specialtyService.get(notValidStringId),
                ServiceUtils.WRONG_ID_MSG);
    }

}