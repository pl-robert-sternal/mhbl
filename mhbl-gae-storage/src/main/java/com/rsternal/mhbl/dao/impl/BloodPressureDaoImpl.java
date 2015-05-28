package com.rsternal.mhbl.dao.impl;

import com.rsternal.mhbl.dao.BloodPressureDao;
import com.rsternal.mhbl.dao.exceptions.AddDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.DaoDataNotFoundException;
import com.rsternal.mhbl.dao.exceptions.DeleteDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.UpdateDaoOperationException;
import com.rsternal.mhbl.dao.model.BloodPressureEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BloodPressureDaoImpl implements BloodPressureDao {

    @PersistenceContext
    private EntityManager em;

    public void add(BloodPressureEntity entity) throws AddDaoOperationException {
        em.getTransaction().begin();
        em.persist(entity);
        em.flush();
        em.getTransaction().commit();
    }

    public List<BloodPressureEntity> findAll() {
        TypedQuery<BloodPressureEntity> q = em.createNamedQuery("BloodPressureEntity.findAll",
                BloodPressureEntity.class);
        List<BloodPressureEntity> results = q.getResultList();
        results = results == null ? new ArrayList<BloodPressureEntity>() : results;

        return results;
    }

    @Override
    public <OWNER> List<BloodPressureEntity> findAllForOwner(OWNER owner) {
        TypedQuery<BloodPressureEntity> q = em.createNamedQuery("BloodPressureEntity.findAllForOwner",
                BloodPressureEntity.class);
        List<BloodPressureEntity> results = q.getResultList();
        results = results == null ? new ArrayList<BloodPressureEntity>() : results;

        return results;
    }

    @Override
    public List<BloodPressureEntity> findAllForTimeScope(Date from, Date to) {
        return null;
    }

    public <T> BloodPressureEntity findById(T id) throws DaoDataNotFoundException {

        return null;
    }

    @Override
    public void delete(BloodPressureEntity e) throws DeleteDaoOperationException {
    }

    @Override
    public void update(BloodPressureEntity e) throws UpdateDaoOperationException {

    }

}
