package com.spw.rr

import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor
import javafx.scene.web.WebEngine

@ArtifactProviderFor(GriffonModel)
class HelpSystemModel {
    WebEngine engine = null
}