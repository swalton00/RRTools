package com.spw.rr

import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import javafx.collections.FXCollections
import javafx.collections.ObservableList

@ArtifactProviderFor(GriffonModel)
class PrefsModel {
    ObservableList<String> choiceBoxList = FXCollections.observableArrayList()
}