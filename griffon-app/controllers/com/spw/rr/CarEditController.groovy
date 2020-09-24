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
import javafx.scene.control.DatePicker
import javafx.stage.Stage

import javax.annotation.Nonnull
import javax.inject.Inject
import java.sql.Date

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
        if (model.newCar) {
            initNewCar()
            if (model.newTag != null) {
                model.carTag.setValue(model.newTag)
            }
        } else {
            initExistingCar(model.carId)
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

    void initNewCar() {
        log.debug("initializing all fields for a new car")
        view.carReportingMark.getSelectionModel().clearSelection()
        model.car = null
        model.newCar = true
        model.carId = null
        model.carTag.set("")
        model.bltDate.set("")
        model.carLength.set("")
        model.carWeight.set("")
        model.carWheels.set("")
        model.carColor.set("")
        model.carNumber.set("")
        model.carDescription.set("")
        view.carPRRType.getSelectionModel().clearSelection()
        view.carAARType.getSelectionModel().clearSelection()
        view.carKitType.getSelectionModel().clearSelection()
        view.carType.getSelectionModel().clearSelection()
        view.carCouplerType.getSelectionModel().clearSelection()
        view.carReportingMark.getSelectionModel().clearSelection()
        view.datePuchased.setValue(null)
        view.inServiceDate.setValue(null)
        view.kitBuiltDate.setValue(null)
    }

    void selectChoice(Integer id, ChoiceBox choice) {
        List<ObsReference> theList = choice.getItems()
        if (id == null) {
            choice.getSelectionModel().clearSelection()
            return
        }
        int index
        for (index = 0; index < theList.size(); index++) {
            ObsReference current = theList.get(index)
            if (current.id.equals(id)) {
                choice.getSelectionModel().select(index)
                break
            }
        }
    }

    private void doText(String carField, SimpleStringProperty property) {
        if (carField != null) {
            property.set(carField)
        } else {
            property.set("")
        }
    }

    private void doIntegerField(Integer carField, SimpleStringProperty property) {
        if (carField != null) {
            property.set(Integer.toString(carField))
        } else {
            property.set("")
        }
    }

    void selectDate(java.sql.Date curDate, DatePicker datePicker) {
        if (curDate == null) {
            datePicker.setValue(null)
            return
        }
        datePicker.setValue(curDate.toLocalDate())
    }

    void initExistingCar(int id) {
        log.debug("initializing for an existing car - id = {}", id)
        RRCar car = dbService.getRRCar(id)
        model.car = car
        model.newCar = false
        model.carId = id
        doText(car.RFIDtag, model.carTag)
        doText(car.bltDate, model.bltDate)
        doIntegerField(car.carLength, model.carLength)
        doIntegerField(car.carWeight, model.carWeight)
        doText(car.carWheels, model.carWheels)
        doText(car.carColor, model.carColor)
        doText(car.carNumber, model.carNumber)
        doText(car.description, model.carDescription)
        selectChoice(car.prrTypeID, view.carPRRType)
        selectChoice(car.aarTypeID, view.carAARType)
        selectChoice(car.kitTypeID, view.carKitType)
        selectChoice(car.carTypeID, view.carType)
        selectDate(car.datePurchased, view.datePuchased)
        selectDate(car.dateKitBuilt, view.kitBuiltDate)
        selectDate(car.dateInService, view.inServiceDate)
        selectChoice(car.couplerTypeID, view.carCouplerType)
        Integer index
        List<ObsReference> theList = view.carReportingMark.getItems()
        view.carReportingMark.getSelectionModel().clearSelection()
        for (index = 0; index < theList.size(); index++) {
            if (theList.get(index).id == car.reportingMarkID) {
                view.carReportingMark.getSelectionModel().select(index)
                break
            }
        }
    }
    Integer decodeChoice(ChoiceBox<ObsReference> choice) {
        if (choice.getSelectionModel().selectedItem != null) {
            return choice.getSelectionModel().getSelectedItem().id
        }
        return null
    }

    String decodeText(SimpleStringProperty modelVal) {
        if (modelVal.isNotEmpty()) {
            return modelVal.getValue()
        }
        return null
    }
    Integer decodeInt(SimpleStringProperty stringVal) {
        if (stringVal.isNotEmpty()) {
            Integer newLength
            try {
                newLength = Integer.parseInt(stringVal.getValue())
                return newLength
            } catch (Exception e) {
                log.error("Bad value entered for Length {}", stringVal.getValue(), e)
                return null
            }
        }
        return null
    }
    Date decodeDate(DatePicker datePicker) {
        if (datePicker.getValue() != null) {
            return Date.valueOf(datePicker.getValue())
        }
        return null
    }

    void refreshModel(RRCar car) {
        if (model.carTag.isNotEmpty()) {
            car.RFIDtag = model.carTag.getValue()
        }
        car.description = decodeText(model.carDescription)
        car.carNumber = decodeText(model.carNumber)
        car.bltDate =decodeText(model.bltDate)
        car.carWheels = decodeText(model.carWheels)
        car.carLength = decodeInt(model.carLength)
        car.carWeight = decodeInt(model.carWeight)
        car.couplerTypeID = decodeChoice(view.carCouplerType)
        car.carTypeID = decodeChoice(view.carType)
        car.aarTypeID = decodeChoice(view.carAARType)
        car.prrTypeID = decodeChoice(view.carPRRType)
        car.kitTypeID = decodeChoice(view.carKitType)
        car.datePurchased = decodeDate(view.datePuchased)
        car.dateInService = decodeDate(view.inServiceDate)
        car.dateKitBuilt = decodeDate(view.kitBuiltDate)
    }

    void doNewSave(Integer markId) {
        RRCar car = new RRCar()
        car.carNumber = model.carNumber.getValue()
        car.reportingMarkID = markId
        refreshModel(car)
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
        log.debug("update car {}", model.car)
        refreshModel(model.car)
        dbService.updateCar(model.car)

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
            doUpdate(model.car.id, markid)
        }
        application.eventRouter.publishEvent('RebuildCarList', [])
        closeWindow()
    }

}