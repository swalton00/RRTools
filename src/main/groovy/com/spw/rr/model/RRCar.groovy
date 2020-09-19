package com.spw.rr.model

import groovy.transform.ToString

import java.sql.Date

@ToString(includeFields = true, includePackage = false)
class RRCar {
    int id
    String carNumber
    String description
    int    reportingMarkID
    Integer    couplerTypeID
    Integer    prrTypeID
    Integer    carTypeID
    Integer    kitTypeID
    Integer    aarTypeID
    Date   datePurchased
    Date   dateInService
    String bltDate
    Integer  carLength
    Integer  carWeight
    String carWheels
    String carColor
    String RFIDtag
    Date   lastUpdated

}
