package com.spw.rr.model

import groovy.transform.ToString

@ToString(includeFields = true, includePackage = false, includeNames = true)
class Preferences {
    String comPort
    String inspectionUnits
    String inspectionFrequency
    String units
    Integer scaleRatio
    String scaleName
    String dbURL
    String dbUsername
    String dbClassName
    String dbPassword
}
