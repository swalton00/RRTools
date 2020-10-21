package com.spw.rr

import com.spw.rr.model.ReportingMark
import com.spw.rr.model.ViewCar
import com.spw.rr.parameter.ViewParameter
import griffon.core.GriffonApplication
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.core.mvc.MVCGroup
import griffon.core.mvc.MVCGroupConfiguration
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.fxml.FXML
import javafx.scene.control.cell.PropertyValueFactory
import javafx.stage.Stage

import javax.inject.Inject


import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonController)
class RrToolsController {
    @MVCMember
    @Nonnull
    RrToolsModel model

    @MVCMember
    @Nonnull
    RrToolsView view


    @Inject
    private DBService dbService

    @Inject
    private SerialDataService dataService

    @Inject
    private PropertyService propertyService

    private boolean initialized = false
    private boolean windowActive = true

    @FXML
    private String statusLine

    MVCGroup prefs = null
    MVCGroup reference = null
    MVCGroup carEdit = null
    MVCGroup inspect = null
    MVCGroup maintain = null
    MVCGroup badOrder = null

    private static final int VIEW_ALL = 0
    private static final int VIEW_COUPLER = 1
    private static final int VIEW_INSPECTION = 2
    private static final int VIEW_MAINTENANCE = 3
    private static final int VIEW_MARK = 4
    private static final int VIEW_NO_TAG = 5

    private static final int MAINT_CAR_TYPE = 0
    private static final int MAINT_AAR_TYPE = 1
    private static final int MAINT_KIT_TYPE = 2
    private static final int MAINT_COUPLER_TYPE = 3
    private static final int MAINT_PRR_TYPE = 4
    private static final String[] MAINT_TITLES = ["Maintain Car Types",
                                                  "Maintian AAR Types",
                                                  "Maintain Kit Types",
                                                  "Maintain Coupler Types",
                                                  "Maintain PRR Types"]
    private static String[] TABLE_NAME = ["CAR_TYPE", "AAR_TYPE", "KIT_TYPE", "COUPLER_TYPE", "PRR_TYPE"]
    private static String[] COLUMN_NAME = ["Car Type", "AAR Type", "Kit Type", "Coupler Type", "PRR Type"]
    private ViewParameter currentView = new ViewParameter()


    MVCGroup getGroup(MVCGroup group, String groupName) {
        if (group != null) {
            return group
        }
        MVCGroup retGroup = application.getMvcGroupManager().findGroup(groupName)
        if (retGroup != null)
            return retGroup
        return createMVCGroup(groupName)
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void prefsAction() {
        log.debug("Showing the prefs window")
        prefs = getGroup(prefs, 'Prefs')
        log.debug("showing the Prefs window")
        application.windowManager.show("prefsWindow")
    }

    public void onInitializeMVCGroup(MVCGroupConfiguration configuration, MVCGroup group) {
        log.debug("initializing the MVC Group")
        if (!initialized) {
            initialized = true
            model.currentView = VIEW_ALL
            String savedComPort = propertyService.getSavedComPort();
            if (savedComPort != null && !savedComPort.equals("<None>")) {
                getLog().debug("setting comm port to {} ", savedComPort);
                dataService.setComPort(savedComPort);
            }
        }
        model.setStatusLine(dataService.getCommPortStatus());

    }

    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    public void onRebuildCarList() {
        log.debug("rebuilding the car list")
        buildCarList()
    }

/**
 * Uses view from currentView (defined above)
 */
    private void buildCarList() {
        log.debug("rebuilding car list")
        List<ViewCar> viewList = dbService.listViewCars(currentView)
        runInsideUIAsync {
            model.tableContents.removeAll()
            model.tableContents.setAll(viewList)
        }
    }


    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    void viewAllCarsAction() {
        log.debug("opening view to all cars")
        currentView.viewSelection = VIEW_ALL
        model.currentView = VIEW_ALL
        buildCarList()
    }

    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    void viewCarsInspectionAction() {
        log.debug("restricting view to cars needing inspection")
        ViewParameter view = new ViewParameter()
        view.viewSelection = VIEW_INSPECTION
        currentView.viewSelection = VIEW_INSPECTION
        buildCarList()
    }

    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    void viewCarsMaintenanceAction() {
        log.debug("restricting view to cars needing maintenance (with a Bad Order)")
        if (model.currentView != VIEW_MAINTENANCE) {
            currentView.viewSelection = VIEW_MAINTENANCE
            model.currentView = VIEW_MAINTENANCE
            buildCarList()
        }
    }

    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    void viewCarsNoTagAction() {
        log.debug("restricting view to cars needing an RFID Tag")
        currentView.viewSelection = VIEW_NO_TAG
        buildCarList()
    }

    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    void viewCarsWithCplrAction() {
        log.debug("restricting view to cars with a specific Coupler Type")
        currentView.viewSelection = VIEW_COUPLER
        buildCarList()
    }

    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    void viewCarsWithRptAction() {
        log.debug("restricting view to cars with a specific Reporting Mark")
        //TODO add code to pull reporting mark and pass in view parameter
    }

    public void onStatus_Update(String newStatus) {
        log.debug("got a status update with {}", newStatus)
        runInsideUISync({ -> model.setStatusLine(newStatus) })
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
        if (name.equals("mainWindow")) {
            log.debug("in the MainWindow code")
            view.carList.setItems(model.tableContents)
            view.rptMark.setCellValueFactory(new PropertyValueFactory<ViewCar, String>("reportingMark"))
            view.carNumber.setCellValueFactory(new PropertyValueFactory<ViewCar, String>("carNumber"))
            view.carType.setCellValueFactory(new PropertyValueFactory<ViewCar, String>("carType"))
            view.aarType.setCellValueFactory(new PropertyValueFactory<ViewCar, String>("aarType"))
            viewAllCarsAction()
        } else {
            windowActive = false
        }
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void onTag_Read(String newTag) {
        log.debug("Main window got a tag of {} ", newTag)
        if (windowActive) {
            boolean foundIt = false
            for (int i = 0; i < model.tableContents.size(); i++) {
                if (model.tableContents.get(i).rfidTag?.equals(newTag)) {
                    view.carList.getSelectionModel().select(i)
                    foundIt = true
                    break
                }
            }
            if (!foundIt) {
                carEditWindow(null, newTag)
            }
        }
    }

    void onWindowHidden(String name, Stage window) {
        log.debug("window hidden - window is {}", name)
        windowActive = true
    }

    public void onShutdownRequested(GriffonApplication application) {
        log.debug("Shutdown has been requested")
        propertyService.saveProperites()
        dataService.doShutdown()
    }

    private void carEditWindow(Integer carId, String newTag) {
        log.debug("creating a new car edit window for car id {}", carId)
        carEdit = getGroup(carEdit, "CarEdit")
        if (carId == null) {
            carEdit.model.windowTitle = "Create a new Car"
            carEdit.model.newCar = true
        } else {
            carEdit.model.windowTitle = "Edit an existing car"
            carEdit.model.newCar = false
        }
        carEdit.model.newTag = newTag
        carEdit.model.carId = carId
        application.windowManager.show("carEditWindow")
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void createNewAction() {
        log.debug("Create a new Car has been requested")
        carEditWindow(null, null)
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void editCarAction() {
        log.debug("Editing the car")
        Integer id = view.carList.getSelectionModel().getSelectedItem().id
        carEditWindow(id, null)
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void closeAction() {
        log.debug("Shutting down now")
        application.shutdown()
    }

    private maintainWindow(int maintType) {
        log.debug("Maintain window creation")
        reference = getGroup(reference, "Reference")
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
    void maintainPRRTypeAction() {
        log.debug("Bring up Maintain PRR Type Window")
        maintainWindow(MAINT_PRR_TYPE)
    }

    @ControllerAction
    void maintainCouplerTypeAction() {
        log.debug("Bring up Coupler Type Window")
        maintainWindow(MAINT_COUPLER_TYPE)
    }

    @ControllerAction
    void inspectCarAction() {
        log.debug("Inspecting the selected car")
        inspect = getGroup(inspect, "Inspection")
        Integer id = view.carList.getSelectionModel().getSelectedItem().id
        inspect.model.carId = id
        inspect.model.carNumber = view.carList.getSelectionModel().getSelectedItem().carNumber
        inspect.model.reportingMark = view.carList.getSelectionModel().getSelectedItem().reportingMark
        application.windowManager.show("InspectionWindow")
    }

    @ControllerAction
    void badOrderCarAction() {
        log.debug("entering a bad order for the selected car")
        badOrder = getGroup(badOrder, "BadOrder")
        ViewCar car = view.carList.getSelectionModel().getSelectedItem()
        Integer id = car.id
        badOrder.model.carId = car.id
        badOrder.model.carNumber.set(car.carNumber)
        badOrder.model.reportingMark.set(car.reportingMark)
        application.windowManager.show("BadOrderWindow")
    }

    @ControllerAction
    void maintainCarAction() {
        log.debug("performing maintenance on selected car")
        maintain = getGroup(maintain, "Maintenance")
        ViewCar car = view.carList.getSelectionModel().getSelectedItem()
        maintain.model.carId = car.id
        maintain.model.reportingMark.set(car.reportingMark)
        maintain.model.carNumber.set(car.carNumber)
        application.windowManager.show("MaintenanceWindow")

    }
}