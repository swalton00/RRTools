package com.spw.rr.model

import groovy.transform.ToString

import java.sql.Date

@ToString(includeFields = true, includePackage = false, includeNames = true)
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
    BigDecimal purchasePrice
    Integer vendor
    Integer manufacturer
    Date   dateKitBuilt
    Date   dateInService
    String bltDate
    Integer  carLength
    Integer  carWeight
    String carWheels
    String carColor
    String RFIDtag
    Date   lastUpdated

}
