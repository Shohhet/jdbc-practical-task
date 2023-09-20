package com.shoggoth.crudapp.model.service.impl;

import com.shoggoth.crudapp.model.entity.DeveloperEntity;
import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.entity.SpecialtyEntity;
import com.shoggoth.crudapp.model.entity.Status;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.repository.DeveloperRepository;
import com.shoggoth.crudapp.model.repository.GenericRepository;
import com.shoggoth.crudapp.model.repository.SkillRepository;
import com.shoggoth.crudapp.model.repository.SpecialtyRepository;
import com.shoggoth.crudapp.model.service.DeveloperService;
import com.shoggoth.crudapp.model.service.util.ServiceUtils;
import com.shoggoth.crudapp.model.service.util.TransactionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperServiceImplTest {
    static final String validFirstName = "Ivan";
    static final String validLastName = "Ivanov";
    static final String notValidFirstName = "12Ivan";
    static final String notValidLastName = "12Ivanov";
    static final String stringId = "1";
    static final String notValidStringId = "1dw";
    static final String specialtyName = "backend";
    static final String skillName = "Java";
    static final Long id = 1L;


    SpecialtyRepository specialtyRepository;
    SkillRepository skillRepository;
    DeveloperRepository developerRepository;
    TransactionUtil transactionUtil;
    DeveloperService developerService;
    List<SkillEntity> skills;
    List<String> skillNames;
    SpecialtyEntity specialty;
    DeveloperEntity developer;


    @BeforeEach
    public void initService() throws SQLException {
        specialtyRepository = Mockito.mock(SpecialtyRepository.class);
        skillRepository = Mockito.mock(SkillRepository.class);
        developerRepository = Mockito.mock(DeveloperRepository.class);
        transactionUtil = Mockito.mock(TransactionUtil.class);
        developerService = new DeveloperServiceImpl(developerRepository, specialtyRepository, skillRepository, transactionUtil);

        ArgumentCaptor<GenericRepository> valueCapture = ArgumentCaptor.forClass(GenericRepository.class);
        Mockito.doNothing().when(transactionUtil).begin(valueCapture.capture());
        Mockito.doNothing().when(transactionUtil).end();
        Mockito.doNothing().when(transactionUtil).commit();
        Mockito.doNothing().when(transactionUtil).rollback();

        SkillEntity skill = new SkillEntity(id, skillName, Status.ACTIVE);
        skills = List.of(skill);
        skillNames = List.of(skillName);
        specialty = new SpecialtyEntity(id, specialtyName, Status.ACTIVE);
        developer = new DeveloperEntity(id, validFirstName, validLastName, skills, specialty, Status.ACTIVE);
    }

    @Test
    public void whenAddingDeveloperDoesNotExistThenReturnOptionalOfDeveloper() throws RepositoryException, ServiceException {
        Mockito.when(developerRepository.add(Mockito.any(DeveloperEntity.class))).thenReturn(Optional.of(developer));
        Mockito.when(skillRepository.getDeveloperSkills(id)).thenReturn(skills);
        Mockito.when(specialtyRepository.getByName(specialtyName)).thenReturn(Optional.of(specialty));
        assertEquals(developer, developerService.add(validFirstName, validLastName, skillNames, specialtyName).get());
    }

    @Test
    public void whenAddingDeveloperAlreadyExistThenReturnOptionalOfEmpty() throws RepositoryException, ServiceException {
        Mockito.when(developerRepository.add(Mockito.any(DeveloperEntity.class))).thenReturn(Optional.empty());
        assertTrue(developerService.add(validFirstName, validLastName, skillNames, specialtyName).isEmpty());
    }

    @Test
    public void whenAddingDeveloperFirstNameNotValidThenThrowServiceException() {
        assertThrows(
                ServiceException.class,
                () -> developerService.add(notValidFirstName, validLastName, skillNames, specialtyName),
                ServiceUtils.WRONG_FIRST_NAME_MSG);
    }

    @Test
    public void whenAddingDeveloperLastNameNotValidThenThrowServiceException() {
        assertThrows(
                ServiceException.class,
                () -> developerService.add(validFirstName, notValidLastName, skillNames, specialtyName),
                ServiceUtils.WRONG_LAST_NAME_MSG);
    }

    @Test
    public void whenUpdatingDeveloperExistThenReturnOptionalOfUpdatedDeveloper() throws RepositoryException, ServiceException {
        Mockito.when(developerRepository.update(developer)).thenReturn(Optional.of(developer));
        Mockito.when(developerRepository.getById(id)).thenReturn(Optional.of(developer));
        Mockito.when(skillRepository.getDeveloperSkills(id)).thenReturn(skills);
        assertEquals(developer, developerService.update(stringId, validFirstName, validLastName, skillNames, specialtyName).get());
    }

    @Test
    public void whenUpdatingDeveloperDoesNotExistThenReturnOptionalOfEmpty() throws RepositoryException, ServiceException {
        Mockito.when(developerRepository.update(developer)).thenReturn(Optional.empty());
        assertTrue(developerService.update(stringId, validFirstName, validLastName, skillNames, specialtyName).isEmpty());
    }

    @Test
    public void whenUpdatingDeveloperFirstNameNotValidThenThrowServiceException() throws RepositoryException {
        Mockito.when(developerRepository.getById(id)).thenReturn(Optional.of(developer));
        assertThrows(
                ServiceException.class,
                () -> developerService.update(stringId, notValidFirstName, validLastName, skillNames, specialtyName),
                ServiceUtils.WRONG_FIRST_NAME_MSG);
    }

    @Test
    public void whenUpdatingDeveloperLastNameNotValidThenThrowServiceException() throws RepositoryException {
        Mockito.when(developerRepository.getById(id)).thenReturn(Optional.of(developer));
        assertThrows(
                ServiceException.class,
                () -> developerService.update(stringId, validFirstName, notValidLastName, skillNames, specialtyName),
                ServiceUtils.WRONG_LAST_NAME_MSG);
    }

    @Test
    public void whenUpdatingDeveloperIdNotValidThenThrowServiceException() {
        assertThrows(
                ServiceException.class,
                () -> developerService.update(notValidStringId, validFirstName, validLastName, skillNames, specialtyName),
                ServiceUtils.WRONG_ID_MSG);
    }

    @Test
    public void whenDeletingDeveloperExistThenReturnTrue() throws ServiceException, RepositoryException {
        Mockito.when(developerRepository.delete(id)).thenReturn(true);
        assertTrue(developerService.delete(stringId));
    }

    @Test
    public void whenDeletingDeveloperDoesNotExistThenReturnFalse() throws RepositoryException, ServiceException {
        Mockito.when(developerRepository.delete(id)).thenReturn(false);
        assertFalse(developerService.delete(stringId));
    }

    @Test
    public void whenDeletingDeveloperIdNotValidThenThrowServiceException() {
        assertThrows(ServiceException.class,
                () -> developerService.delete(notValidStringId),
                ServiceUtils.WRONG_ID_MSG);
    }

    @Test
    public void whenGettingDeveloperExistThenReturnOptionalOfDeveloper() throws ServiceException, RepositoryException {
        Mockito.when(developerRepository.getById(id)).thenReturn(Optional.of(developer));
        assertEquals(developer, developerService.get(stringId).get());
    }

    @Test
    public void whenGettingDeveloperDoesNotExistThenReturnOptionalOfEmpty() throws RepositoryException, ServiceException {
        Mockito.when(developerRepository.getById(id)).thenReturn(Optional.empty());
        assertTrue(developerService.get(stringId).isEmpty());
    }

    @Test
    public void whenGettingDeveloperIdNotValidThenThrowServiceException() {
        assertThrows(ServiceException.class,
                () -> developerService.get(notValidStringId),
                ServiceUtils.WRONG_ID_MSG);
    }

}