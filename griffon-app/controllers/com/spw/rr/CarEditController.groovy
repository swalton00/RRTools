package com.spw.rr

import com.spw.rr.model.Manufacturer
import com.spw.rr.model.ObsReference
import com.spw.rr.model.RRCar
import com.spw.rr.model.ReportingMark
import com.spw.rr.model.Vendor
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.scene.control.ChoiceBox
import javafx.scene.control.DatePicker
import javafx.stage.Stage

import javax.annotation.Nonnull
import javax.inject.Inject
import java.math.RoundingMode
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

    private boolean initialized = false

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
        view.carReportingMark.setValue(null)
    }

    void doVendor() {
        List vendors = dbService.getVendors()
        model.source = FXCollections.observableArrayList()
        vendors.each {
            ObsReference newVendor = new ObsReference()
            newVendor.id = it.id
            newVendor.typeVal = it.vendor
            model.source.add(newVendor)
        }
        view.vendor.setItems(model.source)
    }

    void doManufacturer() {
        List manf = dbService.getManufacturers()
        model.manufacturer = FXCollections.observableArrayList()
        manf.each {
            ObsReference newOne = new ObsReference()
            newOne.id = it.id
            newOne.typeVal = it.manufacturer
            model.manufacturer.add(newOne)
        }
        view.manufacturer.setItems(model.manufacturer)
    }

    private void performInitialization() {
        log.debug("setting up the carEdit window elements")
        model.carLength.addListener({ ObservableValue value, oldValue, newValue ->
            if (newValue.length() == 0) {
                return
            }
            Integer resVal
            try {
                resVal = new BigDecimal(newValue)
                model.messageText.set("")
                model.carLengthDecoded = resVal
            } catch (NumberFormatException ex) {
                newValue = "0"
                model.messageText.set("Length must be numeric, to one decimal place")
                return
            }
        } as javafx.beans.value.ChangeListener)
        model.carWeight.addListener({ ObservableValue value, oldValue, newValue ->
            if (newValue.length() == 0) {
                return
            }
            BigDecimal resVal = new BigDecimal(0.0)
            try {
                resVal = new BigDecimal(newValue)
                model.messageText.set("")
                model.carWeightDecoded = resVal
            } catch (NumberFormatException ex) {
                newValue = "0"
                model.messageText.set("Car length must be numeric, to one decimal place")
                return
            }
        } as javafx.beans.value.ChangeListener)
        model.obsWeightUnits.addListener({ ObservableValue value, oldValue, newValue ->
            if (newValue == oldValue) {
                return
            }
            if (newValue == 0) {
                // switch from grams to ounces
                model.carWeightDecoded = model.carWeightDecoded.divide(new BigDecimal(28.3495), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP)
                model.carWeight.set(model.carWeightDecoded.toString())
            } else {
                // switch from ounces to grams
                model.carWeightDecoded = model.carWeightDecoded.multiply(new BigDecimal(28.3495)).setScale(2, RoundingMode.HALF_UP)
                model.carWeight.set(model.carWeightDecoded.toString())

            }
        } as javafx.beans.value.ChangeListener)
        model.obsLengthUnits.addListener({ ObservableValue value, oldValue, newValue ->
            if (newValue == oldValue) {
                return
            }
            if (newValue == 0) {
                // switch from meters to feet
                model.carLengthDecoded = model.carLengthDecoded.divide(new BigDecimal(0.3048), RoundingMode.HALF_UP).setScale(1, RoundingMode.HALF_UP)
                model.carLength.set(model.carLengthDecoded.toString())
            } else {
                // switch from feet to meters
                model.carLengthDecoded = model.carLengthDecoded.multiply(new BigDecimal(0.3048)).setScale(1, RoundingMode.HALF_UP)
                model.carLength.set(model.carLengthDecoded.toString())
            }
        } as javafx.beans.value.ChangeListener)
        model.purchasePrice.addListener({ ObservableValue value, oldValue, newValue ->
            BigDecimal resVal
            try {
                resVal = new BigDecimal(newValue)
                model.messageText.set("")
                model.purchasePriceDecoded = resVal
            } catch (NumberFormatException ex) {
                newValue = "0.00"
                model.messageText.set("Purchase Price must be numeric, to two decimal places")
                return
            }
        } as javafx.beans.value.ChangeListener)

    }

    void onWindowShown(String name, Stage window) {
        log.debug("in the window shown method for CarEdit")
        if (name.equals("carEditWindow")) {
            if (!initialized) {
                initialized = true
                performInitialization()
            }
            model.messageText.set("")
            model.carTag.setValue("")
            doChoice(view.carType, "CAR_TYPE", model.carType)
            doChoice(view.carAARType, "AAR_TYPE", model.aarType)
            doChoice(view.carKitType, "KIT_TYPE", model.kitType)
            doChoice(view.carCouplerType, "COUPLER_TYPE", model.couplerType)
            doChoice(view.carPRRType, "PRR_TYPE", model.prrType)
            doReporting()
            doVendor()
            doManufacturer()
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
            obs.description = item.typeDescription
            refList.add(obs)
        })
        box.setItems(refList)
    }

    void initNewCar() {
        log.debug("initializing all fields for a new car")
        view.carReportingMark.getSelectionModel().clearSelection()
        view.carId.setText("")
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
        model.purchasePriceDecoded = new BigDecimal(0.00)
        model.purchasePrice.set(0.00)
        model.weathered.set(false)
        model.resistWheels.set(false)
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
        view.carId.setText(car.id.toString())
        model.car = car
        model.newCar = false
        model.carId = id
        doText(car.RFIDtag, model.carTag)
        doText(car.bltDate, model.bltDate)
        if (car.carLength == null) {
            model.carLengthDecoded = new BigDecimal(0.0)
            model.carLength.set("")
        } else {
            if (model.obsLengthUnits.get() == 0) {
                // have feet, leave as is
                model.carLengthDecoded = new BigDecimal(car.carLength)
                model.carLength.set(Integer.toString(car.carLength))
            } else {
                // convert feet to meters
                BigDecimal meterVal = new BigDecimal(car.carLength).multiply(new BigDecimal(0.3048)).setScale(1, RoundingMode.HALF_UP)
                model.carLengthDecoded = meterVal
                model.carLength.set(meterVal.toString())
            }
        }
        if (car.carWeight == null) {
            model.carWeightDecoded = new BigDecimal(0.0)
            model.carWeight.set("")
        } else {
            if (model.obsWeightUnits.get() == 0) {
                // have ounces, leave alone
                model.carWeightDecoded = new BigDecimal(car.carWeight)
                model.carWeight.set(model.carWeightDecoded.toString())

            } else {
                // have ounces, convert to grams
                model.carWeightDecoded = new BigDecimal(car.carWeight).multiply(new BigDecimal(28.3495), RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP)
                model.carWeight.set(model.carWeightDecoded.toString())
            }
        }
        if (car.purchasePrice == null) {
            model.purchasePriceDecoded = new BigDecimal(0.00)
            model.purchasePrice.set("")
        } else {
            model.purchasePriceDecoded = car.purchasePrice
            model.purchasePrice.set(model.purchasePriceDecoded.toString())
        }
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
        model.weathered.set(car.weathered)
        model.resistWheels.set(car.resistanceWheels)
        Integer index
        List<ObsReference> theList = view.carReportingMark.getItems()
        view.carReportingMark.getSelectionModel().clearSelection()
        for (index = 0; index < theList.size(); index++) {
            if (theList.get(index).id == car.reportingMarkID) {
                view.carReportingMark.getSelectionModel().select(index)
                break
            }
        }
        view.manufacturer.getSelectionModel().clearSelection()
        if (car.manufacturer != null) {
            theList = view.manufacturer.getItems()
            for (index = 0; index < theList.size(); index++) {
                if (theList.get(index).id == car.manufacturer) {
                    view.manufacturer.getSelectionModel().select(index)
                }
            }
        }
        view.vendor.getSelectionModel().clearSelection()
        if (car.vendor != null) {
            theList = view.vendor.getItems()
            for (index = 0; index < theList.size(); index++) {
                if (theList.get(index).id == car.vendor) {
                    view.vendor.getSelectionModel().select(index)
                }
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
        car.bltDate = decodeText(model.bltDate)
        car.carWheels = decodeText(model.carWheels)
        car.carColor = decodeText(model.carColor)
        if (!model.carLength.get().equals("")) {
            if (model.obsLengthUnits.get() == 0) {
                // have English, leave alone
                car.carLength = model.carLengthDecoded.setScale(0, RoundingMode.HALF_UP).toInteger()
            } else {
                // convert Feet to meters
                car.carLength = model.carLengthDecoded.multiply(new BigDecimal(0.3048)).setScale(0, RoundingMode.HALF_UP).toInteger()
            }
        } else {
            car.carLength = null
        }
        if (!model.carWeight.get().equals("")) {
            if (model.obsWeightUnits.get() == 0) {
                // leave only (stored in ounces)
                car.carWeight = model.carWeightDecoded.setScale(2, RoundingMode.HALF_UP)
            } else {
                // have grams, convert to ounces
                car.carWeight = model.carWeightDecoded.multiply(new BigDecimal(28.3495)).setScale(2, RoundingMode.HALF_UP).toInteger()
            }
        } else {
            car.carWeight = null
        }
        if (!model.purchasePrice.get().equals("")) {
            car.purchasePrice = model.purchasePriceDecoded.setScale(2, RoundingMode.HALF_UP)
        }
        car.couplerTypeID = decodeChoice(view.carCouplerType)
        car.carTypeID = decodeChoice(view.carType)
        car.aarTypeID = decodeChoice(view.carAARType)
        car.prrTypeID = decodeChoice(view.carPRRType)
        car.kitTypeID = decodeChoice(view.carKitType)
        car.datePurchased = decodeDate(view.datePuchased)
        car.dateInService = decodeDate(view.inServiceDate)
        car.dateKitBuilt = decodeDate(view.kitBuiltDate)
        car.weathered = model.weathered.get()
        car.setWeathered = car.weathered ? "1" : "0"
        car.resistanceWheels = model.resistWheels.get()
        car.setResistanceWheels = model.resistWheels ? "1" : "0"
        Integer selectedItemIndex = view.manufacturer.getSelectionModel().getSelectedIndex()
        if (view.manufacturer.getValue() == null | view.manufacturer.getValue().equals("")) {
            car.manufacturer = null
        } else if (selectedItemIndex == -1) {
            log.debug("adding a Manufacturer")
            car.manufacturer = doNewManufacturer(view.manufacturer.getValue().getTypeVal())
        } else {
            car.manufacturer = view.manufacturer.getItems().get(selectedItemIndex).id
        }
        selectedItemIndex = view.vendor.getSelectionModel().getSelectedIndex()
        if (view.vendor.getValue() == null | view.vendor.getValue().equals("")) {
            car.vendor = null
        } else if (selectedItemIndex == -1) {
            car.vendor = doNewVendor(view.vendor.getValue().getTypeVal())
        } else {
            car.vendor = view.vendor.getItems().get(selectedItemIndex).id
        }
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

    private int doNewManufacturer(String newManf) {
        Manufacturer manf = new Manufacturer()
        manf.manufacturer = newManf
        manf.description = null
        int newManfId = dbService.addManufacturer(manf)
        return newManfId
    }

    private int doNewVendor(String newVendor) {
        Vendor vendor = new Vendor()
        vendor.vendor = newVendor
        vendor.description = null
        int newVendorId = dbService.addVendor(vendor)
    }

    private void doUpdate(int carId) {
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
            doUpdate(model.car.id)
        }
        application.eventRouter.publishEvent('RebuildCarList', [])
        closeWindow()
    }

}