package com.spw.rr.parameter;

import java.sql.Date;
import java.util.ArrayList;

public class BadOrderUpdateParameter {
    Integer id;
    int maintenanceId;
    ArrayList<Integer> boList = new ArrayList<>();
    Date closeDate;

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(" BO Update Parameter ID: ");
        sb.append(id);
        sb.append(" maintenanceId: ");
        sb.append(maintenanceId);
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public ArrayList<Integer> getBoList() {
        return boList;
    }

    public void setBoList(ArrayList<Integer> boList) {
        this.boList = boList;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
}
