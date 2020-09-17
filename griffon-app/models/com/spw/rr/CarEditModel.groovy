package com.spw.rr

import com.spw.rr.model.ObsReference
import com.spw.rr.model.ObservableRefItem
import com.spw.rr.model.ReferenceItem
import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList

@ArtifactProviderFor(GriffonModel)
class CarEditModel {

    Integer carId           // id of car being edited, or null if new car
    String  windowTitle
    boolean newCar = true
    SimpleStringProperty carTag = new SimpleStringProperty("")
    ObservableList<ObsReference> aarType
    ObservableList<ObsReference> carType
    ObservableList<ObsReference> truckType
    ObservableList<ObsReference> couplerType
    ObservableList<ObsReference> kitType
    ObservableList<ObservableRefItem> reportingMark



}