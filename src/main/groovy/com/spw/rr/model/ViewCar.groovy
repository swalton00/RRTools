package com.spw.rr.model

import groovy.transform.ToString

@ToString(includeFields =  true, includePackage = false)
class ViewCar {
    int    id
    String reportingMark
    String carNumber
    String aarType
    String carType
    String carDescription
}
