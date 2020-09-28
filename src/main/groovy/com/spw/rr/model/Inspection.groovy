package com.spw.rr.model

import groovy.transform.ToString

import java.sql.Date
import java.sql.Timestamp

@ToString(includeNames = true, includePackage = false, includeFields = true)
class Inspection {
    int     carId
    Integer inspectionId
    Date inspectionDate
    BigDecimal carWeight
    BigDecimal carLength
    BigDecimal inspectionTime
    char    overallPassed
    char    weightPassed
    char    couplerHeightA
    char    couplerHeightB
    char    couplerActionA
    char    couplerActionB
    char    metalWheelsA
    char    metalWheelsB
    char    resistWheelsA
    char    resistWheelsB
    char    wheelGaugeA
    char    wheelGaugeB
    char    wheelTreadA
    char    wheelTreadB
    char    truckScrewLooseA
    char    truckScrewTightB
    char    carSitsLevel
    char    carDoesntRock
    char    allWheelsTouch
    Timestamp lastUpdated
}
