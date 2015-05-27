package com.rsternal.mhbl.dao;

import com.rsternal.mhbl.dao.exceptions.AddDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.DeleteDaoOperationException;
import com.rsternal.mhbl.dao.exceptions.DaoDataNotFoundException;
import com.rsternal.mhbl.dao.exceptions.UpdateDaoOperationException;

import java.util.List;

public interface Dao<ENTITY> {

    void add(ENTITY id) throws AddDaoOperationException;

    void delete(ENTITY id) throws DeleteDaoOperationException;

    <IDENTITY> ENTITY findById(IDENTITY id) throws DaoDataNotFoundException;

    List<ENTITY> findAll() throws DaoDataNotFoundException;

    void update(ENTITY id) throws UpdateDaoOperationException;
}
