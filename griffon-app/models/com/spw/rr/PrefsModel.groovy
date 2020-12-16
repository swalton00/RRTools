package com.spw.rr

import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

@ArtifactProviderFor(GriffonModel)
class PrefsModel {

    Boolean databaseChanged = false

    ObservableList<String> choiceBoxList = FXCollections.observableArrayList()

    SimpleStringProperty scaleRatio = new SimpleStringProperty("87")

    SimpleStringProperty inspectionUnits = new SimpleStringProperty("")

    SimpleStringProperty inspectionEvery = new SimpleStringProperty("")

    SimpleStringProperty dbUsername = new SimpleStringProperty("")

    SimpleStringProperty dbPassword = new SimpleStringProperty("")

    SimpleStringProperty dbURL = new SimpleStringProperty("")

    SimpleStringProperty dbLocation = new SimpleStringProperty("")

    SimpleStringProperty dbName = new SimpleStringProperty("")

    SimpleBooleanProperty useDbLocation = new SimpleBooleanProperty(true)

    SimpleBooleanProperty useDbUrl = new SimpleBooleanProperty(false)

    DbChangeListener dbChangeListener

    PrefsModel() {
        dbChangeListener = DbChangeListener.getInstance()
        dbChangeListener.setChanged(databaseChanged)
        dbLocation.addListener(dbChangeListener)
        dbUsername.addListener(dbChangeListener)
        dbURL.addListener(dbChangeListener)
        dbPassword.addListener(dbChangeListener)
        dbLocation.addListener(dbChangeListener)
        dbName.addListener(dbChangeListener)
    }

}