package com.spw.rr

import griffon.core.GriffonApplication
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.core.mvc.MVCGroup
import griffon.core.mvc.MVCGroupConfiguration
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.fxml.FXML
import javafx.stage.Stage

import javax.inject.Inject


import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonController)
class RrToolsController {
    @MVCMember
    @Nonnull
    RrToolsModel model


    @Inject
    private SerialDataService dataService

    @Inject
    private PropertyService propertyService

    private boolean initialized = false

    @FXML
    private String statusLine

    MVCGroup prefs = null
    MVCGroup reference = null
    MVCGroup carEdit = null
    private static final int MAINT_CAR_TYPE = 0
    private static final int MAINT_AAR_TYPE = 1
    private static final int MAINT_KIT_TYPE = 2
    private static final int MAINT_COUPLER_TYPE = 3
    private static final String[] MAINT_TITLES = ["Maintain Car Types",
        "Maintian AAR Types",
        "Maintain Kit Types",
        "Maintain Coupler Types"]
    private static String[] TABLE_NAME = ["CAR_TYPE", "AAR_TYPE", "KIT_TYPE", "COUPLER_TYPE"]
    private static String[] COLUMN_NAME = ["Car Type", "AAR Type", "Kit Type", "Coupler Type"]


    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void prefsAction() {
        log.debug("Showing the prefs window")
        if (prefs == null) {
            prefs = application.getMvcGroupManager().findGroup("Prefs")
            if (prefs == null) {
                log.debug("Creating the Prefs MVC Group")
                prefs = createMVCGroup("Prefs")
                log.debug("Prefs created {}", prefs)
            }
        }
        log.debug("showing the Prefs window")
        def windowManager = application.getWindowManager()
        windowManager.show("prefsWindow")
    }

    public void onInitializeMVCGroup(MVCGroupConfiguration configuration, MVCGroup group) {
        log.debug("initializing the MVC Group")
        if (!initialized) {
            initialized = true
            String savedComPort = propertyService.getSavedComPort();
            if (savedComPort != null && !savedComPort.equals("<None>")) {
                getLog().debug("setting comm port to {} ", savedComPort);
                dataService.setComPort(savedComPort);
            }
        }
        model.setStatusLine(dataService.getCommPortStatus());
    }

    public void onStatus_Update(String newStatus) {
        log.debug("got a status update with {}", newStatus)
        runInsideUISync({ -> model.setStatusLine(newStatus)})
    }

    void onWindowShown(String name, Stage window) {
        log.debug("in On Window shown with window {}", window)
        if (!initialized) {
            initialized = true
            String savedComPort = propertyService.getSavedComPort();
            if (savedComPort != null && !savedComPort.equals("<None>")) {
                getLog().debug("setting comm port to {} ", savedComPort);
                dataService.setCommPort(savedComPort);
            }
            model.setStatusLine(dataService.getCommPortStatus());
        }
    }

    public void onShutdownRequested(GriffonApplication application) {
        log.debug("Shutdown has been requested")
        propertyService.saveProperites()
        dataService.doShutdown()
    }

    private void carEditWindow(Integer carId) {
        log.debug("creating a new car edit window for car id {}", carId)
        if (carEdit == null) {
            log.debug("finding an existing car edit window")
            carEdit = application.getMvcGroupManager().findGroup("CarEdit")
            if (carEdit == null) {
                log.debug("didn't find an existing Group - creating a new one")
                carEdit = application.getMvcGroupManager().createMVCGroup("CarEdit")
            }
        }
        if (carId == null) {
            carEdit.model.windowTitle = "Create a new Car"
        } else {
            carEdit.model.windowTitle = "Edit an existing car"
        }
        carEdit.model.carId = carId
        application.windowManager.show("carEditWindow")
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void createNewAction() {
        log.debug("Create a new Car has been requested")
        carEditWindow(null)
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void editCarAction() {
        log.debug("Editing the car")

    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void closeAction() {
        log.debug("Shutting down now")
        application.shutdown()
    }

    private maintainWindow(int maintType) {
        log.debug("Maintain window creation")
        if (reference == null) {
            reference = application.getMvcGroupManager().findGroup("Reference")
            if (reference == null) {
                reference = application.getMvcGroupManager().createMVCGroup("Reference")
            }
        }
        reference.model.windowTitle = MAINT_TITLES[maintType]
        reference.model.referenceType = maintType
        reference.model.referenceTable = TABLE_NAME[maintType]
        reference.model.columnName = COLUMN_NAME[maintType]
        application.windowManager.show("referenceWindow")
    }

    @ControllerAction
    void maintainCarTypeAction() {
        log.debug("Bring up Maintain Car Type Window")
        maintainWindow(MAINT_CAR_TYPE)
    }

    @ControllerAction
    void maintainAARTypeAction() {
        log.debug("Bring up AAR Type Window")
        maintainWindow(MAINT_AAR_TYPE)
    }


    @ControllerAction
    void maintainKitTypeAction() {
        log.debug("Bring up Maintain Kit Type Window")
        maintainWindow(MAINT_KIT_TYPE)
    }


    @ControllerAction
    void maintainCouplerTypeAction() {
        log.debug("Bring up Coupler Type Window")
        maintainWindow(MAINT_COUPLER_TYPE)
    }

}