package com.rsternal.mhbl.web.controllers;

import com.rsternal.mhbl.main.service.Service;
import com.rsternal.mhbl.main.service.exceptions.AddServiceOperationException;
import com.rsternal.mhbl.main.service.exceptions.ServiceDataNotFoundException;
import dao.model.measures.BloodPressure;
import dao.model.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mhbl")
public class MhblController {

    @Autowired
    @Qualifier("measuresService")
    private Service<BloodPressure> measuresService;

    @Autowired
    @Qualifier("userService")
    private Service<User> userService;

    @RequestMapping(value = "/measure/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public BloodPressure getMeasure(@PathVariable int id) {

        List<BloodPressure> measures = null;
        try {
            measures = measuresService.findAll();
        } catch (ServiceDataNotFoundException e) {
            e.printStackTrace();
        }
        return measures != null && measures.size() > id ? measures.get(id) : null;
    }

    @RequestMapping(value = "/measure", method = RequestMethod.GET, headers = "Accept=application/json")
    @PreAuthorize("permitAll")
    @ResponseBody
    public List<BloodPressure> getAllMeasures() {
        List<BloodPressure> measures = null;
        try {
            measures = measuresService.findAll();
        } catch (ServiceDataNotFoundException e) {
            e.printStackTrace();
        }
        return measures;
    }

    @RequestMapping(value = "/measure", method = RequestMethod.PUT, headers = "Accept=application/json")
    @PreAuthorize("permitAll")
    @ResponseBody
    public BloodPressure addBloodPressure(@RequestBody BloodPressure bloodPressure) {
        try {
            measuresService.add(bloodPressure);
        } catch (AddServiceOperationException e) {
            e.printStackTrace();
        }

        return bloodPressure;
    }
}