package com.spw.rr.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Inspection {
    int     carId;
    Integer inspectionId;
    Date inspectionDate;
    BigDecimal carWeight;
    BigDecimal carLength;
    BigDecimal inspectionTime;
    char    overallPassed;
    char    weightPassed;
    char    couplerHeightA;
    char    couplerHeightB;
    char    couplerActionA;
    char    couplerActionB;
    char    metalWheelsA;
    char    metalWheelsB;
    char    resistWheelsA;
    char    resistWheelsB;
    char    wheelGaugeA;
    char    wheelGaugeB;
    char    wheelTreadA;
    char    wheelTreadB;
    char    truckScrewLooseA;
    char    truckScrewTightB;
    char    carSitsLevel;
    char    carDoesntRock;
    char    allWheelsTouch;
    Timestamp lastUpdated;

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Inspection carID: ");
        sb.append(carId);
        sb.append(" InspectionId");
        sb.append(inspectionId);
        sb.append(" Date: ");
        sb.append(inspectionDate);
        return sb.toString();
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Integer getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Integer inspectionId) {
        this.inspectionId = inspectionId;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public BigDecimal getCarWeight() {
        return carWeight;
    }

    public void setCarWeight(BigDecimal carWeight) {
        this.carWeight = carWeight;
    }

    public BigDecimal getCarLength() {
        return carLength;
    }

    public void setCarLength(BigDecimal carLength) {
        this.carLength = carLength;
    }

    public BigDecimal getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(BigDecimal inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public char getOverallPassed() {
        return overallPassed;
    }

    public void setOverallPassed(char overallPassed) {
        this.overallPassed = overallPassed;
    }

    public char getWeightPassed() {
        return weightPassed;
    }

    public void setWeightPassed(char weightPassed) {
        this.weightPassed = weightPassed;
    }

    public char getCouplerHeightA() {
        return couplerHeightA;
    }

    public void setCouplerHeightA(char couplerHeightA) {
        this.couplerHeightA = couplerHeightA;
    }

    public char getCouplerHeightB() {
        return couplerHeightB;
    }

    public void setCouplerHeightB(char couplerHeightB) {
        this.couplerHeightB = couplerHeightB;
    }

    public char getCouplerActionA() {
        return couplerActionA;
    }

    public void setCouplerActionA(char couplerActionA) {
        this.couplerActionA = couplerActionA;
    }

    public char getCouplerActionB() {
        return couplerActionB;
    }

    public void setCouplerActionB(char couplerActionB) {
        this.couplerActionB = couplerActionB;
    }

    public char getMetalWheelsA() {
        return metalWheelsA;
    }

    public void setMetalWheelsA(char metalWheelsA) {
        this.metalWheelsA = metalWheelsA;
    }

    public char getMetalWheelsB() {
        return metalWheelsB;
    }

    public void setMetalWheelsB(char metalWheelsB) {
        this.metalWheelsB = metalWheelsB;
    }

    public char getResistWheelsA() {
        return resistWheelsA;
    }

    public void setResistWheelsA(char resistWheelsA) {
        this.resistWheelsA = resistWheelsA;
    }

    public char getResistWheelsB() {
        return resistWheelsB;
    }

    public void setResistWheelsB(char resistWheelsB) {
        this.resistWheelsB = resistWheelsB;
    }

    public char getWheelGaugeA() {
        return wheelGaugeA;
    }

    public void setWheelGaugeA(char wheelGaugeA) {
        this.wheelGaugeA = wheelGaugeA;
    }

    public char getWheelGaugeB() {
        return wheelGaugeB;
    }

    public void setWheelGaugeB(char wheelGaugeB) {
        this.wheelGaugeB = wheelGaugeB;
    }

    public char getWheelTreadA() {
        return wheelTreadA;
    }

    public void setWheelTreadA(char wheelTreadA) {
        this.wheelTreadA = wheelTreadA;
    }

    public char getWheelTreadB() {
        return wheelTreadB;
    }

    public void setWheelTreadB(char wheelTreadB) {
        this.wheelTreadB = wheelTreadB;
    }

    public char getTruckScrewLooseA() {
        return truckScrewLooseA;
    }

    public void setTruckScrewLooseA(char truckScrewLooseA) {
        this.truckScrewLooseA = truckScrewLooseA;
    }

    public char getTruckScrewTightB() {
        return truckScrewTightB;
    }

    public void setTruckScrewTightB(char truckScrewTightB) {
        this.truckScrewTightB = truckScrewTightB;
    }

    public char getCarSitsLevel() {
        return carSitsLevel;
    }

    public void setCarSitsLevel(char carSitsLevel) {
        this.carSitsLevel = carSitsLevel;
    }

    public char getCarDoesntRock() {
        return carDoesntRock;
    }

    public void setCarDoesntRock(char carDoesntRock) {
        this.carDoesntRock = carDoesntRock;
    }

    public char getAllWheelsTouch() {
        return allWheelsTouch;
    }

    public void setAllWheelsTouch(char allWheelsTouch) {
        this.allWheelsTouch = allWheelsTouch;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
