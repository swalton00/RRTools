package com.spw.rr

import com.spw.rr.model.ObsReference
import com.spw.rr.model.ObservableRefItem
import com.spw.rr.model.RRCar
import com.spw.rr.model.ReferenceItem
import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.beans.value.ObservableValueBase
import javafx.collections.ObservableList

@ArtifactProviderFor(GriffonModel)
class CarEditModel {

    Integer carId           // id of car being edited, or null if new car
    RRCar car
    String  windowTitle
    boolean newCar = true
    SimpleStringProperty carTag = new SimpleStringProperty("")
    ObservableList<ObsReference> reportingMark
    ObservableList<ObsReference> aarType
    ObservableList<ObsReference> carType
    ObservableList<ObsReference> prrType
    ObservableList<ObsReference> couplerType
    ObservableList<ObsReference> kitType
    SimpleStringProperty bltDate = new SimpleStringProperty("")
    SimpleStringProperty carLength = new SimpleStringProperty("")
    SimpleStringProperty carWeight = new SimpleStringProperty("")
    SimpleStringProperty carWheels = new SimpleStringProperty("")
    SimpleStringProperty carColor = new SimpleStringProperty("")
    SimpleStringProperty carNumber = new SimpleStringProperty("")
    SimpleStringProperty carDescription = new SimpleStringProperty("")

}