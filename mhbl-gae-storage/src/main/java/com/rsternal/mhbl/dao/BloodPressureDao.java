package com.rsternal.mhbl.dao;

import com.rsternal.mhbl.dao.model.BloodPressureEntity;

import java.util.Date;
import java.util.List;

public interface BloodPressureDao extends Dao<BloodPressureEntity> {

    <OWNER> List<BloodPressureEntity> findAllForOwner(OWNER owner);

    List<BloodPressureEntity> findAllForTimeScope(Date from, Date to);
}
