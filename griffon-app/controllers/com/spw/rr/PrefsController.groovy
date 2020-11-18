package com.spw.rr

import com.spw.rr.model.Preferences
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

    boolean comPortChanged = false

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
        model.inspectionUnits.bind(view.inspectionUnits.selectionModel.selectedItemProperty())
        model.inspectionEvery.bind(view.inspectionFrequency.selectionModel.selectedItemProperty())
        Preferences currentPrefs = propertyService.getAllProperties()
        model.scaleRatio.set(Integer.toString(currentPrefs.scaleRatio))
        view.unitSystemList.selectionModel.select(currentPrefs.units)
        view.scaleName.selectionModel.select(currentPrefs.scaleName)
        view.inspectionUnits.selectionModel.select(currentPrefs.inspectionUnits)
        view.inspectionFrequency.selectionModel.select(currentPrefs.inspectionFrequency)
        view.comPortList.selectionModel.select(currentPrefs.comPort)
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
        propertyService.saveProperties()
        closePrefs()
    }

    @ControllerAction
    void prefsDoneAction() {
        log.debug("saving the Preferences")
        if ((view.inspectionUnits.selectionModel.selectedItem != null) & (view.inspectionFrequency.selectionModel.selectedItem != null)) {
            propertyService.setInspection(view.inspectionUnits.selectionModel.getSelectedItem(),
            view.inspectionFrequency.selectionModel.getSelectedItem())
        }
        if (!model.scaleRatio.get().isEmpty()) {
            Integer newScaleRatio = 87
            try {
                newScaleRatio = new Integer(model.scaleRatio.get())
            } catch (NumberFormatException nfe) {
                log.debug("bad scale ratio entered")
            }
        }
        if (view.unitSystemList.getSelectionModel().selectedIndexProperty() != null) {
            propertyService.setSavedUnits(view.unitSystemList.getSelectionModel().selectedIndexProperty().get())
        }
        String port = view.comPortList.getSelectionModel().getSelectedItem()
        setCommPort(port)
        closePrefs()
    }
}