package com.rsternal.mhbl.dao.model.builders;


import com.rsternal.mhbl.dao.model.BloodPressureEntity;

import java.util.Date;

public class BloodPressureEntityBuilder {

    private String owner;

    private int systolic;

    private int diastolic;

    private int pulse;

    private Date occurence;

    private String description;

    public BloodPressureEntityBuilder withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public BloodPressureEntityBuilder withSystolic(int systolic) {
        this.systolic = systolic;
        return this;
    }

    public BloodPressureEntityBuilder withDiastolic(int diastolic) {
        this.diastolic = diastolic;
        return this;
    }

    public BloodPressureEntityBuilder withPulse(int pulse) {
        this.pulse = pulse;
        return this;
    }

    public BloodPressureEntityBuilder withOccurence(Date occurence) {
        this.occurence = occurence;
        return this;
    }

    public BloodPressureEntityBuilder withDescription(String desciption) {
        this.description = desciption;
        return this;
    }

    public BloodPressureEntity build() {
        BloodPressureEntity entity = new BloodPressureEntity();
        entity.setOwner(this.owner);
        entity.setSystolic(this.systolic);
        entity.setDiastolic(this.diastolic);
        entity.setPulse(this.pulse);
        entity.setOccurrence(this.occurence);
        entity.setDescription(this.description);
        return entity;
    }
}
