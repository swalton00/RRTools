package com.spw.rr

import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor

@ArtifactProviderFor(GriffonModel)
class ReferenceModel {
    @FXObservable String clickCount = "0"
}