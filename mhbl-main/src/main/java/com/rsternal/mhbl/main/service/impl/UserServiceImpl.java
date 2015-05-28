package com.rsternal.mhbl.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.rsternal.mhbl.dao.Dao;
import com.rsternal.mhbl.dao.exceptions.AddDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.DaoDataNotFoundException;
import com.rsternal.mhbl.dao.exceptions.UpdateDaoOperationException;
import com.rsternal.mhbl.dao.model.UserEntity;
import com.rsternal.mhbl.dao.model.builders.UserEntityBuilder;
import com.rsternal.mhbl.main.service.Service;
import com.rsternal.mhbl.main.service.exceptions.AddServiceOperationException;
import com.rsternal.mhbl.main.service.exceptions.DeleteServiceOperationException;
import com.rsternal.mhbl.main.service.exceptions.ServiceDataNotFoundException;
import com.rsternal.mhbl.main.service.exceptions.UpdateServiceOperationException;

import dao.model.builders.security.UserBuilder;
import dao.model.security.User;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements Service<User> {

    @Autowired
    @Qualifier("userDao")
    private Dao<UserEntity> dao;

    @Override
    public void add(User ve) throws AddServiceOperationException {
        try {
            this.findById(ve.getLogin());
        } catch (ServiceDataNotFoundException e) {
            addNewUser(ve);
        }
    }

    @Override
    public void delete(User ve) throws DeleteServiceOperationException {

    }

    @Override
    public <IDENTITY> User findById(IDENTITY id) throws ServiceDataNotFoundException {

        User user;
        try {
            UserEntity u = dao.findById(id);
            user = new UserBuilder()
                    .withFirstName(u.getFirstName())
                    .withLastName(u.getLastName())
                    .withLogin(u.getLogin())
                    .withEmail(u.getEmail())
                    .withCreatedDate(u.getCreatedDate())
                    .withClosedDate(u.getClosedDate())
                    .withActive(u.isActive())
                    .withPassword(u.getPassword())
                    .build();
        } catch (DaoDataNotFoundException e) {
            throw new ServiceDataNotFoundException(e);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws ServiceDataNotFoundException {
        List<UserEntity> userEntities;
        List<User> users = new ArrayList<>();
        try {
            userEntities = dao.findAll();
        } catch (DaoDataNotFoundException e) {
            throw new ServiceDataNotFoundException(e);
        }

        for (UserEntity entity : userEntities) {
            users.add(new UserBuilder()
                    .withFirstName(entity.getFirstName())
                    .withLastName(entity.getLastName())
                    .withLogin(entity.getLogin())
                    .withEmail(entity.getEmail())
                    .withCreatedDate(entity.getCreatedDate())
                    .withClosedDate(entity.getClosedDate())
                    .withActive(entity.isActive())
                    .withPassword(entity.getPassword())
                    .build());
        }

        return users;
    }

    @Transactional(value = "transactionManager")
    @Override
    public void update(User newUserSetup) throws UpdateServiceOperationException {
        try {
            UserEntity currentUserSetup = dao.findById(newUserSetup.getLogin());
            currentUserSetup.setFirstName(newUserSetup.getFirstName());
            currentUserSetup.setLastName(newUserSetup.getLastName());
            currentUserSetup.setCreatedDate(newUserSetup.getCreatedDate());
            currentUserSetup.setClosedDate(calculateCloseDate(newUserSetup, new UserBuilder()
                    .withActive(currentUserSetup.isActive()).withClosedDate(currentUserSetup.getClosedDate()).build()));
            currentUserSetup.setPassword(newUserSetup.getPassword());
            currentUserSetup.setActive(newUserSetup.isActive());
            dao.update(currentUserSetup);
        } catch (DaoDataNotFoundException | UpdateDaoOperationException e) {
            throw new UpdateServiceOperationException(e);
        }
    }

    private Date calculateCloseDate(User newUserSetup, User currentUserSetup) {
        Date calculatedCloseDate = currentUserSetup.getClosedDate();

        if (currentUserSetup.isActive() && !newUserSetup.isActive()) {
            calculatedCloseDate = new Date();
        } else if (!currentUserSetup.isActive() && newUserSetup.isActive()) {
            calculatedCloseDate = null;
        }

        return calculatedCloseDate;
    }

    private void addNewUser(User newUser) throws AddServiceOperationException {
        UserEntity user = new UserEntityBuilder()
                .withFirstName(newUser.getFirstName())
                .withLastName(newUser.getLastName())
                .withLogin(newUser.getLogin())
                .withEmail(newUser.getEmail())
                .withCreatedDate(new Date())
                .withActive(newUser.isActive())
                .withPassword(newUser.getPassword())
                .build();
        try {
            dao.add(user);
        } catch (AddDaoOperationException x) {
            throw new AddServiceOperationException(x);
        }
    }
}
