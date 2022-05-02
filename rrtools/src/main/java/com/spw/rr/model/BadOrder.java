package com.spw.rr.model;

import java.sql.Date;
import java.sql.Timestamp;

public class BadOrder {
    Integer id;
    int carId;
    int problemArea;
    java.sql.Date entryDate;
    java.sql.Date closeDate;
    String inEffect;
    String outOfService;
    String problemDescription;
    Timestamp lastUpdated;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ");
        sb.append(id);
        sb.append("Car ID:");
        sb.append(carId);
        sb.append(" Problem:");
        sb.append(problemArea);
        sb.append("Entry: ");
        sb.append(entryDate);
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getProblemArea() {
        return problemArea;
    }

    public void setProblemArea(int problemArea) {
        this.problemArea = problemArea;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getInEffect() {
        return inEffect;
    }

    public void setInEffect(String inEffect) {
        this.inEffect = inEffect;
    }

    public String getOutOfService() {
        return outOfService;
    }

    public void setOutOfService(String outOfService) {
        this.outOfService = outOfService;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
