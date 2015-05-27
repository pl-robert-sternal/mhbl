package com.rsternal.mhbl.dao.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.Extension;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Date;

@Entity
@Extension(vendorName = "datanucleus", key="read-write", value = "true")
@NamedQueries({
        @NamedQuery(name = "BloodPressureEntity.findAll", query = "SELECT b FROM BloodPressureEntity AS b"),
        @NamedQuery(name = "BloodPressureEntity.findAllForOwner", query = "SELECT b FROM BloodPressureEntity AS b WHERE b.owner=:owner")
})
public class BloodPressureEntity implements Serializable {

	private static final long serialVersionUID = 1313028300361875884L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key identifier;

    @Column
    private String owner;

    @Column
    private int systolic;

    @Column
    private int diastolic;

    @Column
    private int pulse;

    @Column
    private Date occurrence;

    @Column
    private String description;

    public Key getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Key identifier) {
        this.identifier = identifier;
    }

    public int getSystolic() {
        return this.systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return this.diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public int getPulse() {
        return this.pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public Date getOccurrence() {
        return this.occurrence;
    }

    public void setOccurrence(Date occurrence) {
        this.occurrence = occurrence;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
