package com.spw.rr.model;

public class ViewCar {
    int id;
    String reportingMark;
    String rfidTag;
    String carNumber;
    String aarType;
    String carType;
    String carDescription;

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Mark: ");
        sb.append(reportingMark);
        sb.append("; Number: ");
        sb.append(carNumber);
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportingMark() {
        return reportingMark;
    }

    public void setReportingMark(String reportingMark) {
        this.reportingMark = reportingMark;
    }

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getAarType() {
        return aarType;
    }

    public void setAarType(String aarType) {
        this.aarType = aarType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }
}
