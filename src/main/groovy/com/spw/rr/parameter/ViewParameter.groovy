package com.spw.rr.parameter

import groovy.transform.ToString

@ToString(includeFields = true, includePackage = false, includeNames = true)
class ViewParameter {
    int viewSelection
    String reportingMark
    Integer couplerType
    java.sql.Date inspectionDate
}
