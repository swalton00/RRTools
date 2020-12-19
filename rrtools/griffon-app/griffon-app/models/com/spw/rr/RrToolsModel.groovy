package com.spw.rr

import com.spw.rr.model.ViewCar
import griffon.core.artifact.GriffonModel
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonModel)
class RrToolsModel {
    private ObservableList<ViewCar> tableContents = FXCollections.observableArrayList()

    private StringProperty statusLine = null

    static int currentView

    @Nonnull
    public final StringProperty statusLineProperty() {
        log.debug("in the empty call to StatusLineProperty")
        if (statusLine == null) {
            log.debug("status line was null")
            statusLine = new SimpleStringProperty(this, "statusLine", "OK")
        }
        log.debug("returning status line value of {}", statusLine)
        return statusLine
    }

    public void setStatusLine(String status) {
        statusLineProperty().set(status)
    }

    public String getStatusLine() {
        return statusLineProperty().get()
    }
}