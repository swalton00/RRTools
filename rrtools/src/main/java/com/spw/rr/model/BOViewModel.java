package com.spw.rr.model;

public class BOViewModel {
    String reportingMark;
    String carNumber;
    String date_entered;
    boolean selected;
    String carArea;
    String problemDescription;
    int problemNumber;

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Rpt Mark:");
        sb.append(reportingMark);
        sb.append(" Car Number: ");
        sb.append(carNumber);
        return sb.toString();
    }

    public String getReportingMark() {
        return reportingMark;
    }

    public void setReportingMark(String reportingMark) {
        this.reportingMark = reportingMark;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getDate_entered() {
        return date_entered;
    }

    public void setDate_entered(String date_entered) {
        this.date_entered = date_entered;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getCarArea() {
        return carArea;
    }

    public void setCarArea(String carArea) {
        this.carArea = carArea;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public int getProblemNumber() {
        return problemNumber;
    }

    public void setProblemNumber(int problemNumber) {
        this.problemNumber = problemNumber;
    }
}
