package com.rsternal.mhbl.main.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rsternal.mhbl.dao.exceptions.AddDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.DaoDataNotFoundException;
import com.rsternal.mhbl.dao.exceptions.UpdateDaoOperationException;
import com.rsternal.mhbl.dao.impl.UserDaoImpl;
import com.rsternal.mhbl.dao.model.UserEntity;
import com.rsternal.mhbl.dao.model.builders.UserEntityBuilder;
import com.rsternal.mhbl.main.service.exceptions.AddServiceOperationException;
import com.rsternal.mhbl.main.service.exceptions.ServiceDataNotFoundException;
import com.rsternal.mhbl.main.service.exceptions.UpdateServiceOperationException;
import com.rsternal.mhbl.main.service.impl.UserServiceImpl;

import dao.model.builders.security.UserBuilder;
import dao.model.security.User;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDaoImpl dao;

    @InjectMocks
    private UserServiceImpl service = new UserServiceImpl();

    @SuppressWarnings("unchecked")
	@Test
    public void shouldAddNewUserWhenGivenUserDoesNotExistInDatabase()
            throws DaoDataNotFoundException, AddServiceOperationException, AddDaoOperationException {
        // given
        ArgumentCaptor<String> loginCapture = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<UserEntity> userEntityCapture = ArgumentCaptor.forClass(UserEntity.class);
        User user = new UserBuilder()
                .withFirstName("Jan")
                .withLastName("Kowalski")
                .withLogin("jan@kowalski.pl")
                .withEmail("jan@kowalski.pl")
                .withPassword("password")
                .withActive(true)
                .build();
        when(dao.findById(anyString())).thenThrow(DaoDataNotFoundException.class);
        Calendar calendarZeroPoint = GregorianCalendar.getInstance();
        Calendar calendarCreatedUser = GregorianCalendar.getInstance();
        calendarZeroPoint.setTime(new Date());
        calendarZeroPoint.set(Calendar.SECOND, calendarZeroPoint.get(Calendar.SECOND) - 1);

        // when
        service.add(user);

        // then
        verify(dao).findById(loginCapture.capture());
        verify(dao).add(userEntityCapture.capture());
        assertEquals(loginCapture.getValue(), user.getLogin());
        assertEquals(userEntityCapture.getValue().getFirstName(), user.getFirstName());
        assertEquals(userEntityCapture.getValue().getLastName(), user.getLastName());
        assertEquals(userEntityCapture.getValue().getLogin(), user.getLogin());
        assertEquals(userEntityCapture.getValue().getEmail(), user.getEmail());
        assertEquals(userEntityCapture.getValue().getEmail(), user.getLogin());
        assertEquals(userEntityCapture.getValue().getPassword(), user.getPassword());
        assertEquals(userEntityCapture.getValue().getClosedDate(), null);
        assertEquals(userEntityCapture.getValue().isActive(), user.isActive());
        calendarCreatedUser.setTime(userEntityCapture.getValue().getCreatedDate());
        assertTrue(calendarZeroPoint.before(calendarCreatedUser));
    }

    @Test
    public void shouldNotAddNewUserWhenGivenUserExistInDatabase()
            throws DaoDataNotFoundException, AddServiceOperationException, AddDaoOperationException {
        // given
        User user = new User();
        UserEntity userEntity = new UserEntity();
        when(dao.findById(anyString())).thenReturn(userEntity);

        // when
        service.add(user);

        // then
        verify(dao).findById(anyString());
        verify(dao, times(0)).add(any(UserEntity.class));
    }

    @SuppressWarnings("unchecked")
	@Test(expected = AddServiceOperationException.class)
    public void shouldThrowAddServiceOperationExceptionBecauseInAddMethodInDaoOccuredAnyFailure()
            throws AddDaoOperationException, DaoDataNotFoundException, AddServiceOperationException {
        // given
        when(dao.findById(anyString())).thenThrow(DaoDataNotFoundException.class);
        doThrow(AddDaoOperationException.class).when(dao).add(any(UserEntity.class));

        // when
        service.add(new User());

        // then
        verify(dao).findById(anyString());
        verify(dao).add(any(UserEntity.class));
    }

    @Test
    public void shouldUpdateExistingUser()
            throws ParseException, UpdateServiceOperationException, UpdateDaoOperationException, DaoDataNotFoundException {
        // given
        SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY HH:mm:ss");
        ArgumentCaptor<UserEntity> userCapture = ArgumentCaptor.forClass(UserEntity.class);
        UserEntity oldUserSetup = new UserEntityBuilder()
                .withFirstName("Jan")
                .withLastName("Kowalski")
                .withLogin("jan.kowalski")
                .withEmail("jan@kowalski.pl")
                .withPassword("tajnehaslo")
                .withCreatedDate(sdf.parse("21-07-2007 11:58:23"))
                .withActive(true)
                .build();
        User newUserSetup = new UserBuilder()
                .withFirstName("Adam")
                .withLastName("Nowak")
                .withEmail("adam@nowak.pl")
                .withPassword("haslotajne")
                .withCreatedDate(sdf.parse("17-06-2014 08:23:11"))
                .withActive(oldUserSetup.isActive())
                .build();
        when(dao.findById(anyString())).thenReturn(oldUserSetup);
        doNothing().when(dao).update(any(UserEntity.class));

        // when
        service.update(newUserSetup);

        // then
        verify(dao).update(userCapture.capture());

        /**
         * The main edge business condition
         */
        assertEquals(userCapture.getValue().getClosedDate(), oldUserSetup.getClosedDate());

        assertEquals(userCapture.getValue().getLogin(), oldUserSetup.getLogin());
        assertEquals(userCapture.getValue().getEmail(), oldUserSetup.getEmail());
        assertEquals(userCapture.getValue().getCreatedDate(), oldUserSetup.getCreatedDate());
        assertEquals(userCapture.getValue().getFirstName(), newUserSetup.getFirstName());
        assertEquals(userCapture.getValue().getLastName(), newUserSetup.getLastName());
        assertEquals(userCapture.getValue().getPassword(), newUserSetup.getPassword());
        assertEquals(userCapture.getValue().isActive(), newUserSetup.isActive());
    }


    @Test
    public void shouldUpdateExistingUserAndSetClosedDataIntoRealTimeBecauseUserHasBeenDeactivated()
            throws ParseException, DaoDataNotFoundException, UpdateDaoOperationException, UpdateServiceOperationException {
        // given
        ArgumentCaptor<UserEntity> userCapture = ArgumentCaptor.forClass(UserEntity.class);
        UserEntity oldUserSetup = new UserEntityBuilder()
                .withActive(true)
                .build();
        User newUserSetup = new UserBuilder()
                .withActive(false)
                .build();
        Calendar calendarZeroPoint = GregorianCalendar.getInstance();
        Calendar calendarUpdatedUser = GregorianCalendar.getInstance();
        calendarZeroPoint.setTime(new Date());
        calendarZeroPoint.set(Calendar.SECOND, calendarZeroPoint.get(Calendar.SECOND) - 1);
        when(dao.findById(anyString())).thenReturn(oldUserSetup);
        doNothing().when(dao).update(any(UserEntity.class));

        // when
        service.update(newUserSetup);

        // then
        verify(dao).update(userCapture.capture());

        /**
         * The main edge business condition
         */
        assertNotNull(userCapture.getValue().getClosedDate());

        calendarUpdatedUser.setTime(userCapture.getValue().getClosedDate());
        assertTrue(calendarZeroPoint.before(calendarUpdatedUser));
        assertEquals(userCapture.getValue().isActive(), false);
    }


    @Test
    public void shouldUpdateExistingUserAndSetClosedDataIntoNullBecauseUserHasBeenActivated()
            throws ParseException, DaoDataNotFoundException, UpdateDaoOperationException, UpdateServiceOperationException {
        // given
        ArgumentCaptor<UserEntity> userCapture = ArgumentCaptor.forClass(UserEntity.class);
        UserEntity oldUserSetup = new UserEntityBuilder()
                .withClosedDate(new Date())
                .withActive(false)
                .build();
        User newUserSetup = new UserBuilder()
                .withActive(true)
                .build();
        when(dao.findById(anyString())).thenReturn(oldUserSetup);
        doNothing().when(dao).update(any(UserEntity.class));

        // when
        service.update(newUserSetup);

        // then
        verify(dao).update(userCapture.capture());

        /*
         * The main edge business condition
         */
        assertNull(userCapture.getValue().getClosedDate());

        assertEquals(userCapture.getValue().isActive(), true);
    }

    @Test
    public void shouldNotTouchClosedDataInUserEntityBecauseActivePropertyIsTrueAndSameAfterUpdate()
            throws DaoDataNotFoundException, UpdateDaoOperationException, UpdateServiceOperationException {
        // given
        ArgumentCaptor<UserEntity> userCapture = ArgumentCaptor.forClass(UserEntity.class);
        Date closedDate = new Date();
        UserEntity oldUserSetup = new UserEntityBuilder()
                .withClosedDate(closedDate)
                .withActive(true)
                .build();
        User newUserSetup = new UserBuilder()
                .withActive(true)
                .build();
        when(dao.findById(anyString())).thenReturn(oldUserSetup);
        doNothing().when(dao).update(any(UserEntity.class));

        // whenR
        service.update(newUserSetup);

        // then
        verify(dao).update(userCapture.capture());

        /**
         * The main edge business condition
         */
        assertEquals(userCapture.getValue().getClosedDate(), closedDate);

        assertNotNull(userCapture.getValue().getClosedDate());
        assertEquals(userCapture.getValue().isActive(), newUserSetup.isActive());
        assertTrue(userCapture.getValue().isActive());
    }

    @Test
    public void shouldNotTouchClosedDataInUserEntityBecauseActivePropertyIsFalseAndSameAfterUpdate()
            throws DaoDataNotFoundException, UpdateDaoOperationException, UpdateServiceOperationException {
        // given
        ArgumentCaptor<UserEntity> userCapture = ArgumentCaptor.forClass(UserEntity.class);
        Calendar calendarZeroPoint = GregorianCalendar.getInstance();
        calendarZeroPoint.setTime(new Date());
        calendarZeroPoint.set(Calendar.SECOND, calendarZeroPoint.get(Calendar.SECOND) - 1);
        Date closedDate = calendarZeroPoint.getTime();
        UserEntity oldUserSetup = new UserEntityBuilder()
                .withClosedDate(closedDate)
                .withActive(false)
                .build();
        User newUserSetup = new UserBuilder()
                .withActive(false)
                .build();
        when(dao.findById(anyString())).thenReturn(oldUserSetup);
        doNothing().when(dao).update(any(UserEntity.class));

        // when
        service.update(newUserSetup);

        // then
        verify(dao).update(userCapture.capture());

        /*
         * The main edge business condition
         */
        assertEquals(userCapture.getValue().getClosedDate(), closedDate);

        assertEquals(userCapture.getValue().isActive(), newUserSetup.isActive());
        assertFalse(userCapture.getValue().isActive());
    }

    @SuppressWarnings("unchecked")
	@Test(expected = UpdateServiceOperationException.class)
    public void shouldThrowUpdateServiceOperationExceptionBecauseGivenUserDoesNotExistInDatabase()
            throws DaoDataNotFoundException, UpdateServiceOperationException, UpdateDaoOperationException {

        // given
        when(dao.findById(any(UserEntity.class))).thenThrow(DaoDataNotFoundException.class);

        // when
        service.update(new User());

        // then
        verify(dao, times(0)).update(any(UserEntity.class));
    }

    @Test(expected = UpdateServiceOperationException.class)
    public void shouldThrowUpdateServiceOperationExceptionBecauseSomeExceptionHasBeenOccurredDuringUpdate()
            throws UpdateDaoOperationException, UpdateServiceOperationException, DaoDataNotFoundException {

        // given
        when(dao.findById(any(UserEntity.class))).thenReturn(new UserEntity());
        doThrow(UpdateDaoOperationException.class).when(dao).update(any(UserEntity.class));

        // when
        service.update(new User());

        // then
        verify(dao).update(any(UserEntity.class));
    }

    @Test
    public void shouldFindUser() throws DaoDataNotFoundException, ServiceDataNotFoundException {

        // given
        UserEntity foundUser = new UserEntityBuilder()
                .withLogin("jas.fasola")
                .build();
        ArgumentCaptor<String> loginCapture = ArgumentCaptor.forClass(String.class);
        when(dao.findById(any(UserEntity.class))).thenReturn(foundUser);

        // when
        User user = service.findById("jas.fasola");

        // then
        verify(dao).findById(loginCapture.capture());
        assertEquals(loginCapture.getValue(), "jas.fasola");
        assertNotNull(user);
        assertEquals(user.getLogin(), "jas.fasola");
    }

	@SuppressWarnings("unchecked")
	@Test(expected = ServiceDataNotFoundException.class)
    public void shouldNotFindUserInDatabaseForGivenUser() throws DaoDataNotFoundException, ServiceDataNotFoundException {

        // given
        when(dao.findById(anyString())).thenThrow(DaoDataNotFoundException.class);

        // when
        service.findById("any.login");

        // then
        verify(dao).findById(anyString());
    }

    @Test
    public void shouldReturnUserListBecauseUsersExistsInDatabase()
            throws DaoDataNotFoundException, ServiceDataNotFoundException {

        // given
        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());
        users.add(new UserEntity());
        when(dao.findAll()).thenReturn(users);

        // when
        List<User> foundUsers =  service.findAll();

        // then
        verify(dao).findAll();
        assertEquals(foundUsers.size(), 2);
    }

    @SuppressWarnings("unchecked")
	@Test(expected = ServiceDataNotFoundException.class)
    public void shouldNotFindAnyUserBecauseAnyUserExistInDatabase()
            throws DaoDataNotFoundException, ServiceDataNotFoundException {

        // given
        when(dao.findAll()).thenThrow(DaoDataNotFoundException.class);

        // when
        service.findAll();

        // then
        verify(dao).findAll();
    }
}
