package com.spw.rr.model

import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false, includeFields = true)
class ExportCar {

    String reporting
    String carNumber
    String carType
    Integer carLength
    BigDecimal carWeight
    String carColor
    String builtDate
    String idTag
}
