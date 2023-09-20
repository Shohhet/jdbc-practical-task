package com.shoggoth.crudapp.model.service.impl;

import com.shoggoth.crudapp.model.entity.SkillEntity;
import com.shoggoth.crudapp.model.entity.Status;
import com.shoggoth.crudapp.model.exceptions.RepositoryException;
import com.shoggoth.crudapp.model.exceptions.ServiceException;
import com.shoggoth.crudapp.model.repository.DeveloperRepository;
import com.shoggoth.crudapp.model.repository.GenericRepository;
import com.shoggoth.crudapp.model.repository.SkillRepository;
import com.shoggoth.crudapp.model.service.SkillService;
import com.shoggoth.crudapp.model.service.util.ServiceUtils;
import com.shoggoth.crudapp.model.service.util.TransactionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SkillServiceImplTest {
    static final String skillName = "Java";
    static final String notValidSkillName = "2424eszfsz";
    static final String stringId = "1";
    static final String notValidStringId = "1ef";
    static final Long id = 1L;
    SkillRepository skillRepository;
    DeveloperRepository developerRepository;
    TransactionUtil transactionUtil;
    SkillService skillService;
    SkillEntity skill;


    @BeforeEach
    public void initService() throws SQLException {
        skillRepository = Mockito.mock(SkillRepository.class);
        developerRepository = Mockito.mock(DeveloperRepository.class);
        transactionUtil = Mockito.mock(TransactionUtil.class);
        skillService = new SkillServiceImpl(skillRepository, transactionUtil);
        ArgumentCaptor<GenericRepository> valueCapture = ArgumentCaptor.forClass(GenericRepository.class);
        Mockito.doNothing().when(transactionUtil).begin(valueCapture.capture());
        Mockito.doNothing().when(transactionUtil).end();
        Mockito.doNothing().when(transactionUtil).commit();
        Mockito.doNothing().when(transactionUtil).rollback();

        skill = new SkillEntity(id, skillName, Status.ACTIVE);
    }

    @Test
    public void whenAddingSkillDoesNotExistThenReturnOptionalOfSkill() throws RepositoryException, ServiceException {
        Mockito.when(skillRepository.add(Mockito.any(SkillEntity.class))).thenReturn(Optional.of(skill));
        assertEquals(skill, skillService.add(skillName).get());
    }

    @Test
    public void whenAddingSkillAlreadyExistThenReturnOptionalOfEmpty() throws RepositoryException, ServiceException {
        Mockito.when(skillRepository.add(Mockito.any(SkillEntity.class))).thenReturn(Optional.empty());
        assertTrue(skillService.add(skillName).isEmpty());
    }

    @Test
    public void whenAddingSkillNameNotValidThenThrowServiceException() {
        assertThrows(
                ServiceException.class,
                () -> skillService.add(notValidSkillName),
                ServiceUtils.WRONG_SKILL_NAME_MSG);
    }

    @Test
    public void whenUpdatingSkillExistThenReturnOptionalOfUpdatedSkill() throws RepositoryException, ServiceException {
        Mockito.when(skillRepository.update(Mockito.any(SkillEntity.class))).thenReturn(Optional.of(skill));
        assertEquals(skill, skillService.update(stringId, skillName).get());
    }

    @Test
    public void whenUpdatingSkillDoesNotExistThenReturnOptionalOfEmpty() throws RepositoryException, ServiceException {
        Mockito.when(skillRepository.update(Mockito.any(SkillEntity.class))).thenReturn(Optional.empty());
        assertTrue(skillService.update(stringId, skillName).isEmpty());
    }

    @Test
    public void whenUpdatingSkillNameNotValidThenThrowServiceException() {
        assertThrows(
                ServiceException.class,
                () -> skillService.update(stringId, notValidSkillName),
                ServiceUtils.WRONG_SKILL_NAME_MSG);
    }

    @Test
    public void whenUpdatingSkillIdNotValidThenThrowServiceException() {
        assertThrows(
                ServiceException.class,
                () -> skillService.update(notValidStringId, skillName),
                ServiceUtils.WRONG_ID_MSG);
    }

    @Test
    public void whenDeletingSkillExistThenReturnTrue() throws ServiceException, RepositoryException {
        Mockito.when(skillRepository.delete(id)).thenReturn(true);
        assertTrue(skillService.delete(stringId));
    }

    @Test
    public void whenDeletingSkillDoesNotExistThenReturnFalse() throws RepositoryException, ServiceException {
        Mockito.when(skillRepository.delete(id)).thenReturn(false);
        assertFalse(skillService.delete(stringId));
    }

    @Test
    public void whenDeletingSkillIdNotValidThenThrowServiceException() {
        assertThrows(ServiceException.class,
                () -> skillService.delete(notValidStringId),
                ServiceUtils.WRONG_ID_MSG);
    }

    @Test
    public void whenGettingSkillExistThenReturnOptionalOfSkill() throws ServiceException, RepositoryException {
        Mockito.when(skillRepository.getById(id)).thenReturn(Optional.of(skill));
        assertEquals(skill, skillService.get(stringId).get());
    }

    @Test
    public void whenGettingSkillDoesNotExistThenReturnOptionalOfEmpty() throws RepositoryException, ServiceException {
        Mockito.when(skillRepository.getById(id)).thenReturn(Optional.empty());
        assertTrue(skillService.get(stringId).isEmpty());
    }

    @Test
    public void whenGettingSkillIdNotValidThenThrowServiceException() {
        assertThrows(ServiceException.class,
                () -> skillService.get(notValidStringId),
                ServiceUtils.WRONG_ID_MSG);
    }

}