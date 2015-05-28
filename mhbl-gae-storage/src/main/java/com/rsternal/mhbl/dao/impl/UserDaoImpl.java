package com.rsternal.mhbl.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.rsternal.mhbl.dao.Dao;
import com.rsternal.mhbl.dao.exceptions.AddDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.DaoDataNotFoundException;
import com.rsternal.mhbl.dao.exceptions.DeleteDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.UpdateDaoOperationException;
import com.rsternal.mhbl.dao.model.UserEntity;
import org.springframework.transaction.annotation.Transactional;

public class UserDaoImpl implements Dao<UserEntity> {

    @PersistenceContext
    private EntityManager em;

    @Transactional(value = "transactionManager")
    @Override
    public void add(UserEntity e) throws AddDaoOperationException {
        try {
            em.persist(e);
        } catch (Exception x) {
            throw new AddDaoOperationException(x);
        }
    }

    @Override
    public void delete(UserEntity e) throws DeleteDaoOperationException {

    }

    @Override
    public <T> UserEntity findById(T id) throws DaoDataNotFoundException {
        TypedQuery<UserEntity> q = em.createNamedQuery("UserEntity.findByLogin", UserEntity.class);
        q.setParameter("login", id);

        UserEntity user;
        try {
            user = q.getSingleResult();
        } catch (NoResultException e) {
            throw new DaoDataNotFoundException(e);
        }

        return user;
    }

    @Override
    public List<UserEntity> findAll() throws DaoDataNotFoundException {
        TypedQuery<UserEntity> q = em.createNamedQuery("UserEntity.findAll", UserEntity.class);
        List<UserEntity> users = q.getResultList();

        if (users == null) {
            throw new DaoDataNotFoundException("No users found");
        }

        return users;
    }

    @Override
    public void update(UserEntity e) throws UpdateDaoOperationException {
    }
}
