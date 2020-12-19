package com.spw.rr.model

import groovy.transform.ToString

import java.sql.Timestamp

@ToString(includeFields = true, includePackage = false, includeNames = true)
class MaintenanceItem {
    Integer id
    int      carId
    BigDecimal timeRequired
    Date      datePerformed
    Integer   carArea
    String    problemDescription
    String    workPerformed
    String    badOrdersClosed
    Timestamp lastUpdated
}
