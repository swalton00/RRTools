package com.spw.rr

import com.spw.rr.model.Inspection
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.beans.value.ObservableValue
import javafx.stage.Stage

import javax.annotation.Nonnull
import javax.inject.Inject
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@ArtifactProviderFor(GriffonController)
class InspectionController {
    @MVCMember
    @Nonnull
    InspectionModel model

    @MVCMember
    @Nonnull
    InspectionView view

    @Inject
    DBService dbService

    private boolean initialized = false

    private void initInspection() {
        view.inspectionDate.setValue(LocalDate.now())
        model.carLengthDecoded = new BigDecimal(0.0)
        model.carWeightDecoded = new BigDecimal(0.0)
        model.timeRequiredDecoded = new BigDecimal(0.0)
        model.weightCalculated.set("")
        model.timeRequired.set("")
        model.carWeight.set("")
        model.carLength.set("")
        model.doesWeightPass.set(false)
        model.couplerHeightPassA.set(false)
        model.couplerHeightPassB.set(false)
        model.couplerActionPassA.set(false)
        model.couplerActionPassB.set(false)
        model.metalWheelsA.set(false)
        model.metalWheelsB.set(false)
        model.resistanceWheelsA.set(false)
        model.resistanceWheelsB.set(false)
        model.wheelGaugeA.set(false)
        model.wheelGaugeB.set(false)
        model.wheelTreadA.set(false)
        model.wheelTreadB.set(false)
        model.truckScrewTightB.set(false)
        model.truckScrewLooseA.set(false)
        model.carSitsLevel.set(false)
        model.carDoesNotRock.set(false)
        model.allWheelsTouch.set(false)
        LocalTime currentTime = LocalTime.now()
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("kk:mm")
        model.startTime.set(currentTime.format(formatter))
        model.inspectionMessage.setValue("")
    }

    private void checkWeight() {
        if ((!model.carWeightDecoded.equals(0.0) & !model.carLengthDecoded.equals(0.0))) {
            if (model.carWeightDecoded > model.calculatedWeight) {
                model.doesWeightPass.set(true)
            } else {
                model.doesWeightPass.set(false)
            }
        }
    }

    private void initOnce() {
        log.debug("initializing the first time into inspection")
        initialized = true
        model.carLength.addListener({ ObservableValue value, oldValue, newValue ->
            if (newValue.length() == 0) {
                return
            }
            BigDecimal resVal
            try {
                resVal = new BigDecimal(newValue)
                model.inspectionMessage.set("")
                model.carLengthDecoded = resVal
            } catch (NumberFormatException ex) {
                newValue = "0"
                model.inspectionMessage.set("Length must be entered as digits.digit (0.0)")
                return
            }
            model.calculatedWeight = resVal.divide(new BigDecimal(2)).add(new BigDecimal(1.0)).setScale(1, BigDecimal.ROUND_HALF_UP)
            String calculated = model.calculatedWeight.toString()
            model.weightCalculated.set(calculated)
            checkWeight()
        } as javafx.beans.value.ChangeListener
        )
        model.carWeight.addListener({ ObservableValue value, oldValue, newValue ->
            if (newValue.length() == 0) {
                return
            }
            BigDecimal resVal
            try {
                resVal = new BigDecimal(newValue)
                model.inspectionMessage.set("")
                model.carWeightDecoded = resVal
            } catch (NumberFormatException ex) {
                newValue = "0"
                model.inspectionMessage.set("Weight must be entered as digits.digit (0.0)")
                return
            }
            checkWeight()
        } as javafx.beans.value.ChangeListener
        )
        model.timeRequired.addListener({ ObservableValue value, oldValue, newValue ->
            if (newValue.length() == 0) {
                return
            }
            BigDecimal resVal
            try {
                resVal = new BigDecimal(newValue)
                model.inspectionMessage.set("")
                model.timeRequiredDecoded = resVal
            } catch (NumberFormatException ex) {
                newValue = "0"
                model.inspectionMessage.set("Time required must be entered as digits.digit (0.0)")
                return
            }
        } as javafx.beans.value.ChangeListener
        )
    }


    public void onWindowShown(String name, Stage window) {
        if (!name.equals("InspectionWindow"))
            return
        if (!initialized) {
            initOnce()
        }
        log.debug("setting up for an inspection")
        model.obsReportingMark.setValue(model.reportingMark)
        model.obsCarNumber.setValue(model.carNumber)
        initInspection()
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void cancelInspectionAction() {
        log.debug("canceling out of the inspection")
        application.windowManager.hide("InspectionWindow")
    }

    private Inspection createInspection() {
        Inspection ret = new Inspection()
        ret.carId = model.carId
        ret.inspectionDate = new java.sql.Date(model.inspectionDate.getValue())
        ret.carWeight = model.carWeightDecoded
        ret.carLength = model.carLengthDecoded
        ret.inspectionTime = model.timeRequiredDecoded
        ret.overallPassed = model.overallResults.get() ? "1" : "0"
        ret.weightPassed = model.doesWeightPass.get() ? "1" : "0"
        ret.couplerHeightA = model.couplerHeightPassA.get() ? "1" : "0"
        ret.couplerHeightB = model.couplerHeightPassB.get() ? "1" : "0"
        ret.couplerActionA = model.couplerActionPassA.get() ? "1" : "0"
        ret.couplerActionB = model.couplerActionPassB.get() ? "1" : "0"
        ret.metalWheelsA = model.metalWheelsA.get() ? "1" : "0"
        ret.metalWheelsB = model.metalWheelsB.get() ? "1" : "0"
        ret.resistWheelsA = model.resistanceWheelsA.get() ? "1" : "0"
        ret.resistWheelsB = model.resistanceWheelsB.get() ? "1" : "0"
        ret.wheelGaugeA = model.wheelGaugeA.get() ? "1" : "0"
        ret.wheelGaugeB = model.wheelGaugeB.get() ? "1" : "0"
        ret.wheelTreadA = model.wheelTreadA.get() ? "1" : "0"
        ret.wheelTreadB = model.wheelTreadB.get() ? "1" : "0"
        ret.truckScrewLooseA = model.truckScrewLooseA.get() ? "1" : "0"
        ret.truckScrewTightB = model.truckScrewTightB.get() ? "1" : "0"
        ret.carSitsLevel = model.carSitsLevel.get() ? "1" : "0"
        ret.carDoesntRock = model.carDoesNotRock.get() ? "1" : "0"
        ret.allWheelsTouch =  model.allWheelsTouch.get() ? "1" : "0"
        log.debug("inspection to be returned is {}", ret)
        return ret
    }

    @ControllerAction
    @Threading(Threading.Policy.OUTSIDE_UITHREAD_ASYNC)
    void saveInspectionAction() {
        log.debug("saving the results of the inspection")
        Inspection newItem = createInspection()
        dbService.addInspection(newItem)
        log.debug("successfully added the new inspection")
        application.windowManager.hide("InspectionWindow")
    }

}