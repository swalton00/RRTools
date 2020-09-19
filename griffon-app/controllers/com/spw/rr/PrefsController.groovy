package com.spw.rr

import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.stage.Stage
import javax.inject.Inject
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonController)
class PrefsController {
    @MVCMember @Nonnull
    PrefsModel model

    @MVCMember @Nonnull
    PrefsView view


    @Inject
    private SerialDataService serialDataService
    @Inject
    private PropertyService propertyService

    private void closePrefs() {
        application.getWindowManager().hide("prefsWindow")
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void prefsCancelAction() {
        log.debug("Canceling out of the Prefs window")
        closePrefs()
    }

    void initWindow() {
        log.debug("intializing the Preferences window")
        ArrayList<String> portList = serialDataService.getSerialPortList()
        for (String port : portList) {
            model.choiceBoxList.add(port)
        }
        log.debug("view choiceBox is now {}", view.comPortList)
        view.comPortList.setItems(model.choiceBoxList)
        log.debug("portlist is now {}", model.choiceBoxList)
        model.scaleRatio.bindBidirectional(view.scaleRatio.textProperty())
    }

    void onWindowShown(String windowName, Stage w) {
        log.debug("window shown exit for {} ", windowName)
        if (windowName.equals("prefsWindow")) {
            initWindow()
        }
    }

    void setCommPort(String port) {
        log.debug("Setting the saved com port to {}", port)
        propertyService.setSavedComPort(port)
        serialDataService.setCommPort(port)
        propertyService.saveProperites()
        closePrefs()
    }

    @ControllerAction
    void prefsDoneAction() {
        log.debug("saving the Preferences")
        String port = view.comPortList.getSelectionModel().getSelectedItem()
        setCommPort(port)
        closePrefs()
    }
}