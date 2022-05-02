package com.spw.rr.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class MaintenanceItem {
    Integer id;
    int      carId;
    BigDecimal timeRequired;
    Date datePerformed;
    Integer   carArea;
    String    problemDescription;
    String    workPerformed;
    String    badOrdersClosed;
    Timestamp lastUpdated;

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("MaintenanceItem Id: ");
        sb.append(id);
        sb.append(" Car Id: ");
        sb.append(carId);
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

    public BigDecimal getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(BigDecimal timeRequired) {
        this.timeRequired = timeRequired;
    }

    public Date getDatePerformed() {
        return datePerformed;
    }

    public void setDatePerformed(Date datePerformed) {
        this.datePerformed = datePerformed;
    }

    public Integer getCarArea() {
        return carArea;
    }

    public void setCarArea(Integer carArea) {
        this.carArea = carArea;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getWorkPerformed() {
        return workPerformed;
    }

    public void setWorkPerformed(String workPerformed) {
        this.workPerformed = workPerformed;
    }

    public String getBadOrdersClosed() {
        return badOrdersClosed;
    }

    public void setBadOrdersClosed(String badOrdersClosed) {
        this.badOrdersClosed = badOrdersClosed;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
