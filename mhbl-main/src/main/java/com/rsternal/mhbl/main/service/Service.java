package com.rsternal.mhbl.main.service;

import java.util.List;

import com.rsternal.mhbl.main.service.exceptions.AddServiceOperationException;
import com.rsternal.mhbl.main.service.exceptions.DeleteServiceOperationException;
import com.rsternal.mhbl.main.service.exceptions.ServiceDataNotFoundException;
import com.rsternal.mhbl.main.service.exceptions.UpdateServiceOperationException;

public interface Service<MODEL> {

    void add(MODEL model) throws AddServiceOperationException;

    void delete(MODEL model) throws DeleteServiceOperationException;

    <IDENTITY> MODEL findById(IDENTITY id) throws ServiceDataNotFoundException;

    List<MODEL> findAll() throws ServiceDataNotFoundException;

    void update(MODEL model) throws UpdateServiceOperationException;

}
