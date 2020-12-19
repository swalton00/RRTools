package com.spw.rr

import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField

import java.sql.Date

@ArtifactProviderFor(GriffonModel)
class InspectionModel {
    Integer carId
    BigDecimal carWeightDecoded = new BigDecimal(0.0)
    BigDecimal carLengthDecoded = new BigDecimal(0.0)
    BigDecimal timeRequiredDecoded = new BigDecimal(0.0)
    BigDecimal calculatedWeight

    @FXObservable String carNumber = ""
    @FXObservable String reportingMark = ""
    SimpleStringProperty startTime = new SimpleStringProperty("")
    SimpleStringProperty weightCalculated = new SimpleStringProperty("")
    SimpleStringProperty timeRequired = new SimpleStringProperty("")
    SimpleObjectProperty<Date> inspectionDate = new SimpleObjectProperty<Date>(new java.util.Date().getTime() )
    SimpleStringProperty carWeight = new SimpleStringProperty("")
    SimpleBooleanProperty doesWeightPass = new SimpleBooleanProperty(false)
    SimpleStringProperty carLength = new SimpleStringProperty("")
    SimpleStringProperty obsCarNumber = new SimpleStringProperty("")
    SimpleStringProperty obsReportingMark = new SimpleStringProperty("")
    SimpleBooleanProperty couplerHeightPassA = new SimpleBooleanProperty(false)
    SimpleBooleanProperty couplerHeightPassB = new SimpleBooleanProperty(false)
    SimpleBooleanProperty couplerActionPassA = new SimpleBooleanProperty(false)
    SimpleBooleanProperty couplerActionPassB = new SimpleBooleanProperty(false)
    SimpleBooleanProperty metalWheelsA = new SimpleBooleanProperty(false)
    SimpleBooleanProperty metalWheelsB = new SimpleBooleanProperty(false)
    SimpleBooleanProperty resistanceWheelsA = new SimpleBooleanProperty(false)
    SimpleBooleanProperty resistanceWheelsB = new SimpleBooleanProperty(false)
    SimpleBooleanProperty wheelGaugeA = new SimpleBooleanProperty(false)
    SimpleBooleanProperty wheelGaugeB = new SimpleBooleanProperty(false)
    SimpleBooleanProperty wheelTreadA = new SimpleBooleanProperty(false)
    SimpleBooleanProperty wheelTreadB = new SimpleBooleanProperty(false)
    SimpleBooleanProperty truckScrewTightB = new SimpleBooleanProperty(false)
    SimpleBooleanProperty truckScrewLooseA = new SimpleBooleanProperty(false)
    SimpleBooleanProperty carSitsLevel = new SimpleBooleanProperty(false)
    SimpleBooleanProperty carDoesNotRock = new SimpleBooleanProperty(false)
    SimpleBooleanProperty allWheelsTouch = new SimpleBooleanProperty(false)
    SimpleBooleanProperty overallResults = new SimpleBooleanProperty(false)
    SimpleStringProperty inspectionMessage = new SimpleStringProperty("")

}