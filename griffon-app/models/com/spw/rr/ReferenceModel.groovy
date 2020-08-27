package com.spw.rr

import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor
import com.spw.rr.model.ReferenceItem
import javafx.collections.FXCollections
import javafx.collections.ObservableList

@ArtifactProviderFor(GriffonModel)
class ReferenceModel {
    String windowTitle
    ObservableList<ReferenceItem> tableData = FXCollections.observableArrayList()
    int referenceType
    String referenceTable
    String columnName

}