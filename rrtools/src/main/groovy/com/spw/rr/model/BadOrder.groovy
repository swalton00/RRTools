package com.spw.rr.model

import groovy.transform.ToString

import java.sql.Timestamp

@ToString(includeFields = true, includePackage = false, includeNames = true)
class BadOrder {
    Integer id
    int carId
    int problemArea
    java.sql.Date entryDate
    java.sql.Date closeDate
    String inEffect
    String outOfService
    String problemDescription
    Timestamp lastUpdated
}
