package com.spw.rr.model;

import java.math.BigDecimal;

public class ExportCar {
    String reporting;
    String carNumber;
    String carType;
    Integer carLength;
    BigDecimal carWeight;
    String carColo;
    String builtDate;
    String idTag;

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(" Reporting Mark: ");
        sb.append(reporting);
        sb.append(" Car Number");
        sb.append(carNumber);
        return sb.toString();
    }

    public String getReporting() {
        return reporting;
    }

    public void setReporting(String reporting) {
        this.reporting = reporting;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Integer getCarLength() {
        return carLength;
    }

    public void setCarLength(Integer carLength) {
        this.carLength = carLength;
    }

    public BigDecimal getCarWeight() {
        return carWeight;
    }

    public void setCarWeight(BigDecimal carWeight) {
        this.carWeight = carWeight;
    }

    public String getCarColo() {
        return carColo;
    }

    public void setCarColo(String carColo) {
        this.carColo = carColo;
    }

    public String getBuiltDate() {
        return builtDate;
    }

    public void setBuiltDate(String builtDate) {
        this.builtDate = builtDate;
    }

    public String getIdTag() {
        return idTag;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }
}
