package com.spw.rr

import griffon.core.GriffonApplication
import griffon.core.event.EventHandler
import griffon.exceptions.GriffonViewInitializationException
import javafx.application.Platform

class ApplicationEventHandler implements EventHandler {
    void onUncaughtGriffonViewInitializationException(GriffonViewInitializationException x) {
        Platform.exit()
    }

    void onReadyStart(GriffonApplication application) {
        println("Ready has started")
    }
}