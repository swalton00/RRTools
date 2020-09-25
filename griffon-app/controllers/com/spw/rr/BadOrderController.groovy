package com.spw.rr

import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.stage.Stage

import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonController)
class BadOrderController {
    @MVCMember @Nonnull
    BadOrderModel model

    void onWindowShown(String name, Stage window) {
        log.debug("Bad Order - in On Window shown with window {}", name)
    }

        @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void cancelBadOrderActionTarget() {
        log.debug("canceling out of the bad order window")
        application.windowManager.hide("badOrderWindow")

    }

    @ControllerAction
    @Threading(Threading.Policy.OUTSIDE_UITHREAD_ASYNC)
    void createBadOrderActionTarget() {
        log.debug("creating the bad order record")
        application.windowManager.hide("badOrderWindow")

    }

}