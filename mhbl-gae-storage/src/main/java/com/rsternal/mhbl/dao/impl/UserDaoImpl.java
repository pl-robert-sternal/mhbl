package com.rsternal.mhbl.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.rsternal.mhbl.dao.Dao;
import com.rsternal.mhbl.dao.exceptions.AddDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.DaoDataNotFoundException;
import com.rsternal.mhbl.dao.exceptions.DeleteDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.UpdateDaoOperationException;
import com.rsternal.mhbl.dao.model.UserEntity;

public class UserDaoImpl implements Dao<UserEntity> {

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public void add(UserEntity e) throws AddDaoOperationException {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(e);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception x) {
            throw new AddDaoOperationException(x);
        }
    }

    @Override
    public void delete(UserEntity e) throws DeleteDaoOperationException {

    }

    @Override
    public <T> UserEntity findById(T id) throws DaoDataNotFoundException {
        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
        TypedQuery<UserEntity> q = em.createNamedQuery("UserEntity.findAll", UserEntity.class);
        List<UserEntity> users = q.getResultList();

        if (users == null) {
            throw new DaoDataNotFoundException("No users found");
        }

        return users;
    }

    @Override
    public void update(UserEntity e) throws UpdateDaoOperationException {
        EntityManager em = emf.createEntityManager();
        TypedQuery<UserEntity> q = em.createNamedQuery("UserEntity.findByLogin", UserEntity.class);
        q.setParameter("login", e.getLogin());
        try {
            UserEntity entity = q.getSingleResult();
            em.getTransaction().begin();
            entity.setFirstName(e.getFirstName());
            entity.setLastName(e.getLastName());
            entity.setLogin(e.getLogin());
            entity.setPassword(e.getPassword());
            entity.setClosedDate(e.getClosedDate());
            entity.setActive(e.isActive());
            em.merge(entity);
            em.flush();
            em.getTransaction().commit();
        } catch (Exception x) {
            throw new UpdateDaoOperationException(x);
        }
    }
}
