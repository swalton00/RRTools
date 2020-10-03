package com.spw.rr

import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

import java.time.LocalDate

@ArtifactProviderFor(GriffonModel)
class BadOrderModel {
    Integer carId
    SimpleStringProperty carNumber = new SimpleStringProperty("")
    SimpleStringProperty reportingMark = new SimpleStringProperty("")
    SimpleObjectProperty dateReported = new SimpleObjectProperty(LocalDate.now())
    SimpleIntegerProperty areaChosen = new SimpleIntegerProperty(0)
    SimpleBooleanProperty outOfService = new SimpleBooleanProperty(true)
    SimpleStringProperty problemDescription = new SimpleStringProperty("")
}