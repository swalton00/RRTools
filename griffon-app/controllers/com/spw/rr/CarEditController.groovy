package com.spw.rr

import com.spw.rr.model.ObsReference
import com.spw.rr.model.RRCar
import com.spw.rr.model.ReportingMark
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.ChoiceBox
import javafx.stage.Stage

import javax.annotation.Nonnull
import javax.inject.Inject

@ArtifactProviderFor(GriffonController)
class CarEditController {
    @MVCMember
    @Nonnull
    CarEditModel model

    @MVCMember
    @Nonnull
    CarEditView view

    @Inject
    DBService dbService

    void doReporting() {
        List rptMarks = dbService.getReportingMarks()
        model.reportingMark = FXCollections.observableArrayList()
        rptMarks.each {
            ObsReference newOne = new ObsReference()
            newOne.id = it.id
            newOne.typeVal = it.mark
            model.reportingMark.add(newOne)
        }
        view.carReportingMark.setItems(model.reportingMark)
    }

    void onWindowShown(String name, Stage window) {
        log.debug("in the window shown method for CarEdit")
        if (name.equals("carEditWindow")) {
            model.carTag.setValue("")
            doChoice(view.carType, "CAR_TYPE", model.carType)
            doChoice(view.carAARType, "AAR_TYPE", model.aarType)
            doChoice(view.carKitType, "KIT_TYPE", model.kitType)
            doChoice(view.carCouplerType, "COUPLER_TYPE", model.couplerType)
            doChoice(view.carPRRType, "PRR_TYPE", model.prrType)
            doReporting()
        }
    }


    public void onTag_Read(String newTag) {
        log.debug("got a tag of {} ", newTag)
        runInsideUIAsync {
            model.carTag.setValue(newTag)
        }

    }


    private void closeWindow() {
        log.debug("closing the carEdit window")
        application.windowManager.hide("carEditWindow")
    }

    private void doChoice(ChoiceBox box, String tableName, javafx.collections.ObservableList<ObsReference> refList) {
        List referenceList = dbService.getReferenceList(tableName)
        refList = FXCollections.observableArrayList()
        referenceList.each({ item ->
            ObsReference obs = new ObsReference()
            obs.id = item.id
            obs.typeVal = item.typeName
            refList.add(obs)
        })
        box.setItems(refList)
    }

    void doNewSave(Integer markId) {
        RRCar car = new RRCar()
        car.carNumber = model.carNumber.getValue()
        if (model.carTag.isNotEmpty()) {
            car.RFIDtag = model.carTag.getValue()
        }
        car.reportingMarkID = markId
        car.description = model.carDescription.getValue()
        car.carNumber = model.carNumber.getValue()
        if (model.bltDate.isNotEmpty())
            car.bltDate = model.bltDate.getValue()
        if (model.carWheels.isNotEmpty())
            car.carWheels = model.carWheels.getValue()
        if (model.carColor.isNotEmpty())
            car.carColor = model.carColor.getValue()
        if (model.carLength.isNotEmpty()) {
            Integer newLength
            try {
                newLength = Integer.parseInt(model.carLength.getValue())
                car.carLength = newLength
            } catch (Exception e) {
                log.error("Bad value entered for Length {}", model.carLength.getValue(), e)
            }
        }
        if (model.carWeight.isNotEmpty()) {
            Integer newWeight
            try {
                newWeight = Integer.parseInt(model.carWeight.getValue())
                car.carWeight = newWeight
            } catch (Exception e) {
                log.error("Bad value entered for Weight {}", model.carWeight.getValue(), e)
            }
        }
        if (view.carCouplerType.getSelectionModel().selectedItem != null) {
            car.couplerTypeID = view.carCouplerType.getSelectionModel().getSelectedItem().id
        }
        if (view.carType.getSelectionModel().selectedItem != null) {
            car.carTypeID = view.carType.getSelectionModel().getSelectedItem().id
        }
        if (view.carKitType.getSelectionModel().selectedItem != null) {
            car.kitTypeID = view.carKitType.getSelectionModel().getSelectedItem().id
        }
        if (view.carAARType.getSelectionModel().selectedItem != null) {
            car.aarTypeID = view.carAARType.getSelectionModel().getSelectedItem().id
        }
        if (view.carPRRType.getSelectionModel().selectedItem != null) {
            car.prrTypeID = view.carPRRType.getSelectionModel().getSelectedItem().id
        }
        dbService.addCar(car)
        log.debug("car {} added", car)
    }


    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void cancelChangesAction() {
        log.debug("canceling any changes and closing the car edit window")
        closeWindow()
    }

    private int doNewReportingMark(String newMark) {
        ReportingMark mark = new ReportingMark()
        mark.mark = newMark
        mark.description = null
        int newId = dbService.addReportingMark(mark)
        log.debug("returned value from added mark is {}", newId)
        return newId
    }

    private void doUpdate(int carId, Integer markid) {

    }

    @ControllerAction
    void saveChangesAction() {
        log.debug("saving the changes made to the car")
        def carTypeSelectionItem = view.carType.getSelectionModel().getSelectedItem()
        def carTypeSelectionIndex = view.carType.getSelectionModel().getSelectedIndex()
        def carTypeItems = view.carType.itemsProperty()
        def current = view.carReportingMark.getSelectionModel().getSelectedItem()
        log.debug("selected item is {}", current)
        log.debug("description is now {}", model.carDescription.getValue())
        Integer selectedItemIndex = view.carReportingMark.getSelectionModel().getSelectedIndex()
        Integer markid
        if (view.carReportingMark.getValue() == null | view.carReportingMark.getValue().equals("")) {
            markid = null
            log.error("need to select reporting mark first")
        } else if (selectedItemIndex == -1) {
            log.debug("new reporting mark")
            markid = doNewReportingMark(view.carReportingMark.getValue().getTypeVal())
        } else {
            ObsReference cur = view.carReportingMark.getItems().get(selectedItemIndex)
            log.debug("setting reporting mark to {}", cur)
            markid = cur.id
        }
        if (model.newCar) {
            doNewSave(markid)
        } else {
            doUpdate(carId, markid)
        }
        closeWindow()
    }

}