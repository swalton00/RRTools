package com.spw.rr

import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonController)
class CarEditController {
    @MVCMember @Nonnull
    CarEditModel model

    private closeWindow() {
        log.debug("closing the carEdit window")
        application.windowManager.hide("carEditWindow")
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void cancelChangesAction() {
        log.debug("canceling any changes and closing the car edit window")
        closeWindow()
    }

    @ControllerAction
    void saveChangesAction() {
        log.debug("saving the changes made to the car")
        //TODO: add the save code
        closeWindow()
    }
}