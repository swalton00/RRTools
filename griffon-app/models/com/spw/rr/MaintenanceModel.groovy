package com.spw.rr

import com.spw.rr.model.BOViewModel
import griffon.core.artifact.GriffonModel
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.ObservableList
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections

import java.time.LocalDate

@ArtifactProviderFor(GriffonModel)
class MaintenanceModel {
    Integer carId
    SimpleStringProperty reportingMark = new SimpleStringProperty("")
    SimpleStringProperty carNumber = new SimpleStringProperty("")
    SimpleStringProperty currentTime = new SimpleStringProperty("")
    SimpleObjectProperty datePerformed = new SimpleObjectProperty(LocalDate.now())
    SimpleStringProperty timeRequired = new SimpleStringProperty("")
    SimpleIntegerProperty selectedCarArea = new SimpleIntegerProperty(0)
    SimpleStringProperty probDesc = new SimpleStringProperty("")
    SimpleStringProperty workPerformed = new SimpleStringProperty("")
    SimpleStringProperty messages = new SimpleStringProperty("")
    BigDecimal timeRequiredDecoded = new BigDecimal(0.0)
    ObservableList<BOViewModel> badOrderItems = FXCollections.observableArrayList()
    SimpleBooleanProperty closeSelected = new SimpleBooleanProperty(false)
    SimpleBooleanProperty disableClose = new SimpleBooleanProperty(true)
   // SimpleIntegerProperty countSelected = new SimpleIntegerProperty(0)
    int countSelected = 0
}