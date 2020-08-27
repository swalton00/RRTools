package com.spw.rr

import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor

@ArtifactProviderFor(GriffonModel)
class CarEditModel {

    Integer carId           // id of car being edited, or null if new car
    String  windowTitle
}