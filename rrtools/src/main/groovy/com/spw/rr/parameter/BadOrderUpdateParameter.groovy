package com.spw.rr.parameter

import groovy.transform.ToString

import java.sql.Date

@ToString(includeNames = true, includePackage = false, includeFields = true)
class BadOrderUpdateParameter {
    Integer id
    int maintenanceId
    ArrayList<Integer> boList = new ArrayList<>()
    Date closeDate
}
