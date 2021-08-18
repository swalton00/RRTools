package com.spw.rr

import com.spw.rr.model.Preferences
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import javax.inject.Inject
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonController)
class PrefsController {
    @MVCMember
    @Nonnull
    PrefsModel model

    @MVCMember
    @Nonnull
    PrefsView view

    boolean comPortChanged = false
    boolean ourURLchange = false
    String dbClassname = ""
    String JDBC_PRE = "jdbc:h2:file:"
    String JDBC_POST_1 = "AUTO_SERVER=TRUE;"
    String JDBC_POST_2 = "SCHEMA=RR;"
    String JDBC_POST_3 = "MODE=DB2;"
    String JDBC_POST = JDBC_POST_1 + JDBC_POST_2 + JDBC_POST_3


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

    private void checkForClass(String newVal) {
        dbClassname = ""
        if (newVal.contains("db2")) {
            dbClassname = "com.ibm.db2.jcc.DB2Driver"
        }
        if (newVal.contains("h2")) {
            dbClassname = "org.h2.Driver"
        }
    }

    void setUrl() {
        log.debug("in set URL - UseLoc has value {}", model.useDbLocation.value)
        if (model.useDbLocation.value) {
            log.debug("setting the Database URL based on name and location")
            model.dbURL.set(JDBC_PRE + model.dbLocation.value + "/" + model.dbName.value + ";"+ JDBC_POST)
            log.debug("new URL is now {}", model.dbURL.value)
        }
    }

    ChangeListener focusListener = { ObservableValue observable, oldValue, newValue ->
        if (!newValue) {
            setUrl()
        }
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
        model.dbUsername.set(currentPrefs.dbUsername)
        model.dbPassword.set(currentPrefs.dbPassword)
        model.dbURL.set(currentPrefs.dbURL)
        model.message.set("")
        if (model.dbURL.value.startsWith(JDBC_PRE) &
                model.dbURL.value.endsWith(JDBC_POST)) {
            view.useDbLoc.selected = true
            String tempStr = model.dbURL.value.substring(JDBC_PRE.length())
            int slashPos = tempStr.lastIndexOf("/")
            model.dbName.set(tempStr.substring(slashPos + 1, tempStr.indexOf(";")))
            model.dbLocation.set(tempStr.substring(0, slashPos))
        } else {
            view.useUrl.selected = true
        }
        view.dbName.focusedProperty().addListener(focusListener)
        view.dbLocation.focusedProperty().addListener(focusListener)
        model.dbChangeListener.setChangedValue(false)
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
        String url = model.dbURL.value
        String user = model.dbUsername.value
        String pw = model.dbPassword.value
        if (model.dbChangeListener.getChanged()) {
            DbSetup dbSetup = DbSetup.getInstance()
            String retValue = dbSetup.dbStart(url, user, pw)
            if (retValue != null) {
                runInsideUISync {

                }
                    return
            }
            propertyService.setDbItems([dbURL: url, dbUsername: user, dbPassword: pw])
            runInsideUISync {
                Alert alert = new Alert(Alert.AlertType.WARNING)
                alert.setContentText("The Database information has changed: Press OKAY to terminate and allow a restart, otherwise " +
                        "press ESCape to continue editing the preferences")
                alert.setTitle("Press OKAY to restart")
                Optional<ButtonType> result = alert.showAndWait()
                if (result.isPresent()) {
                    application.shutdown()
                }
                return
            }
        }
        closePrefs()
    }

    @ControllerAction
    void prefsLocationPickerAction() {
        log.debug("Picking the location of the database")
        File choiceMade = null;
        DirectoryChooser chooseLocation = new DirectoryChooser()
        chooseLocation.setTitle("Select the directory containing the database")
        runInsideUISync {
            choiceMade = chooseLocation.showDialog(view.prefsStage);
        }
        if (choiceMade != null) {
            String locName = choiceMade.getPath()
            model.dbLocation.set(locName)
            setUrl()
        }
    }

}