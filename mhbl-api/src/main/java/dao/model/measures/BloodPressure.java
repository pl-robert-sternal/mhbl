package dao.model.measures;

import java.io.Serializable;
import java.util.Date;

public class BloodPressure implements Serializable {

    private static final long serialVersionUID = 1L;

    private int systolic;

    private int diastolic;

    private int pulse;

    private Date occurrence;

    private String description;

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
}
