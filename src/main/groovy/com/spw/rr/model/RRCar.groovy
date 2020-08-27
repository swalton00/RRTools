package com.spw.rr.model

import java.sql.Date


class RRCar {
    int id
    String carNumber
    String description
    int    reportingMarkID
    int    couplerTypeID
    int    truckTypeID
    int    carTypeID
    int    kitTypeID
    int    aarTypeID
    Date   datePurchased
    Date   dateInService
    Float  carLength
    Float  carWeight
    String carWheels
    String RFIDtag
    Date   lastUpdated

}
