package com.rsternal.mhbl.main.service.impl;

import com.rsternal.mhbl.dao.Dao;
import com.rsternal.mhbl.dao.exceptions.AddDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.DaoDataNotFoundException;
import com.rsternal.mhbl.dao.exceptions.DeleteDaoOperationException;
import com.rsternal.mhbl.dao.model.BloodPressureEntity;
import com.rsternal.mhbl.dao.model.builders.BloodPressureEntityBuilder;
import com.rsternal.mhbl.main.service.Service;
import com.rsternal.mhbl.main.service.exceptions.AddServiceOperationException;
import com.rsternal.mhbl.main.service.exceptions.DeleteServiceOperationException;
import com.rsternal.mhbl.main.service.exceptions.ServiceDataNotFoundException;
import com.rsternal.mhbl.main.service.exceptions.UpdateServiceOperationException;
import dao.model.builders.measures.BloodPressureBuilder;
import dao.model.measures.BloodPressure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

public class BloodMeasureServiceImpl implements Service<BloodPressure> {

    @Autowired
    @Qualifier("bloodPressureDao")
    private Dao<BloodPressureEntity> bloodPressureDao;

    public BloodMeasureServiceImpl() {
    }

    public void add(BloodPressure ve) throws AddServiceOperationException {
        BloodPressureEntity bpe = new BloodPressureEntityBuilder().withDescription(ve.getDescription()).withDiastolic(
                ve.getDiastolic()).withOccurence(ve.getOccurrence()).withPulse(ve.getPulse()).withSystolic(
                ve.getSystolic()).build();
        try {
            bloodPressureDao.add(bpe);
        } catch (AddDaoOperationException e) {
            throw new AddServiceOperationException(e);
        }
    }

    public void delete(BloodPressure ve) throws DeleteServiceOperationException {
        BloodPressureEntity entity = new BloodPressureEntityBuilder()
                .withSystolic(ve.getSystolic())
                .withDiastolic(ve.getDiastolic())
                .withPulse(ve.getPulse())
                .withDescription(ve.getDescription())
                .withOccurence(ve.getOccurrence())
                .build();
        try {
            bloodPressureDao.delete(entity);
        } catch (DeleteDaoOperationException e) {
            throw new DeleteServiceOperationException(e);
        }
    }

    public <T> BloodPressure findById(T id) throws ServiceDataNotFoundException {
        throw new ServiceDataNotFoundException();
    }

    public List<BloodPressure> findAll() throws ServiceDataNotFoundException {
        List<BloodPressure> bloodPressures = new ArrayList<BloodPressure>();

        try {
            for (BloodPressureEntity bp : bloodPressureDao.findAll()) {
                bloodPressures.add(new BloodPressureBuilder().withDescription(bp.getDescription()).withDiastolic(
                            bp.getDiastolic()).withOccurrence(bp.getOccurrence()).withPulse(bp.getPulse()).withSystolic(
                            bp.getSystolic()).build());
            }
        } catch (DaoDataNotFoundException e) {
            throw new ServiceDataNotFoundException(e);
        }

        return bloodPressures;
    }

    public void update(BloodPressure ve) throws UpdateServiceOperationException {
        throw new UpdateServiceOperationException();

    }

}
