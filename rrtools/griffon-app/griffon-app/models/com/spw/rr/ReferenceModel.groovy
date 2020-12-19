package com.spw.rr

import com.spw.rr.model.ObservableRefItem
import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import com.spw.rr.model.ReferenceItem
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty

import javafx.collections.FXCollections
import javafx.collections.ObservableList

@ArtifactProviderFor(GriffonModel)
class ReferenceModel {

    SimpleBooleanProperty refEnterNotReady = new SimpleBooleanProperty(true)
    SimpleBooleanProperty refDataClean = new SimpleBooleanProperty(true)
    SimpleBooleanProperty cleanData = new SimpleBooleanProperty(true)
    SimpleBooleanProperty newReady = new SimpleBooleanProperty(false)
    SimpleStringProperty newTypeValue = new SimpleStringProperty("")
    SimpleBooleanProperty newTypedEntered = new SimpleBooleanProperty(false)
    SimpleStringProperty newDescriptionValue = new SimpleStringProperty("")
    SimpleBooleanProperty newDescriptionEntered = new SimpleBooleanProperty(false)
    String windowTitle
    ObservableList<ObservableRefItem> tableData = FXCollections.observableArrayList()
    int referenceType
    String referenceTable
    String columnName



}