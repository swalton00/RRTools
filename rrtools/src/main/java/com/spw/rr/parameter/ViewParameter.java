package com.spw.rr.parameter;

import java.sql.Date;

public class ViewParameter {
    private int viewSelection = 0;
    String reportingMark = null;
    Integer couplerType = null;
    java.sql.Date inspectionDate = null;

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Selection: ");
        sb.append(Integer.toString(viewSelection));
        sb.append("; Mark: ");
        sb.append("; Cplr: ");
        sb.append(couplerType != null ?  Integer.toString(couplerType) : "null");
        sb.append("; Dt: ");
        if (inspectionDate  != null) {
            sb.append(inspectionDate.toString());
        } else {
            sb.append("null;");
        }
        return sb.toString();
    }

    public int getViewSelection() {
        return viewSelection;
    }

    public void setViewSelection(int viewSelection) {
        this.viewSelection = viewSelection;
    }

    public String getReportingMark() {
        return reportingMark;
    }

    public void setReportingMark(String reportingMark) {
        this.reportingMark = reportingMark;
    }

    public Integer getCouplerType() {
        return couplerType;
    }

    public void setCouplerType(Integer couplerType) {
        this.couplerType = couplerType;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
}
