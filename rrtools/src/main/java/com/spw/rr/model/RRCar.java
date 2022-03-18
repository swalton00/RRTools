package com.spw.rr.model;

import java.math.BigDecimal;
import java.sql.Date;

public class RRCar {
    int id;
    String carNumber;
    String description;
    int reportingMarkID;
    Integer couplerTypeID;
    Integer prrTypeID;
    Integer carTypeID;
    Integer kitTypeID;
    Integer aarTypeID;
    Date datePurchased;
    BigDecimal purchasePrice;
    Integer vendor;
    Integer manufacturer;
    Date dateKitBuilt;
    Date dateInService;
    boolean weathered;
    boolean resistanceWheels;
    String bltDate;
    Integer carLength;
    BigDecimal carWeight;
    String carWheels;
    String carColor;
    String RFIDtag;
    Date lastUpdated;
    String setWeathered;
    String setResistanceWheels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReportingMarkID() {
        return reportingMarkID;
    }

    public void setReportingMarkID(int reportingMarkID) {
        this.reportingMarkID = reportingMarkID;
    }

    public Integer getCouplerTypeID() {
        return couplerTypeID;
    }

    public void setCouplerTypeID(Integer couplerTypeID) {
        this.couplerTypeID = couplerTypeID;
    }

    public Integer getPrrTypeID() {
        return prrTypeID;
    }

    public void setPrrTypeID(Integer prrTypeID) {
        this.prrTypeID = prrTypeID;
    }

    public Integer getCarTypeID() {
        return carTypeID;
    }

    public void setCarTypeID(Integer carTypeID) {
        this.carTypeID = carTypeID;
    }

    public Integer getKitTypeID() {
        return kitTypeID;
    }

    public void setKitTypeID(Integer kitTypeID) {
        this.kitTypeID = kitTypeID;
    }

    public Integer getAarTypeID() {
        return aarTypeID;
    }

    public void setAarTypeID(Integer aarTypeID) {
        this.aarTypeID = aarTypeID;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getVendor() {
        return vendor;
    }

    public void setVendor(Integer vendor) {
        this.vendor = vendor;
    }

    public Integer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Integer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getDateKitBuilt() {
        return dateKitBuilt;
    }

    public void setDateKitBuilt(Date dateKitBuilt) {
        this.dateKitBuilt = dateKitBuilt;
    }

    public Date getDateInService() {
        return dateInService;
    }

    public void setDateInService(Date dateInService) {
        this.dateInService = dateInService;
    }

    public boolean isWeathered() {
        return weathered;
    }

    public void setWeathered(boolean weathered) {
        this.weathered = weathered;
    }

    public boolean isResistanceWheels() {
        return resistanceWheels;
    }

    public void setResistanceWheels(boolean resistanceWheels) {
        this.resistanceWheels = resistanceWheels;
    }

    public String getBltDate() {
        return bltDate;
    }

    public void setBltDate(String bltDate) {
        this.bltDate = bltDate;
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

    public String getCarWheels() {
        return carWheels;
    }

    public void setCarWheels(String carWheels) {
        this.carWheels = carWheels;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getRFIDtag() {
        return RFIDtag;
    }

    public void setRFIDtag(String RFIDtag) {
        this.RFIDtag = RFIDtag;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getSetWeathered() {
        return setWeathered;
    }

    public void setSetWeathered(String setWeathered) {
        this.setWeathered = setWeathered;
    }

    public String getSetResistanceWheels() {
        return setResistanceWheels;
    }

    public void setSetResistanceWheels(String setResistanceWheels) {
        this.setResistanceWheels = setResistanceWheels;
    }
}
