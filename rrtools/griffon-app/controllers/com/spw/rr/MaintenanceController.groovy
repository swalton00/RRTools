package com.spw.rr

import com.spw.rr.model.BOViewModel
import com.spw.rr.model.MaintenanceItem
import com.spw.rr.model.ObsReference
import com.spw.rr.model.ReferenceItem
import com.spw.rr.parameter.BadOrderUpdateParameter
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.beans.property.IntegerProperty
import javafx.beans.value.ObservableValue
import javafx.stage.Stage

import javax.annotation.Nonnull
import javax.inject.Inject
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@ArtifactProviderFor(GriffonController)
class MaintenanceController {
    @MVCMember @Nonnull
    MaintenanceModel model

    @MVCMember
    MaintenanceView view

    @Inject
    DBService dbService

    boolean initialized = false

    def changeListener = {  ObservableValue value, oldValue, newValue ->
        if (oldValue.equals(newValue))
            return
        if (newValue) {
            model.countSelected++
            view.closeSelected.disableProperty().set(false)
        } else {
            model.countSelected--
            if (model.countSelected < 1) {
                model.countSelected = 0
                view.closeSelected.disableProperty().set(true)
            }
        }
        log.debug("current use count is {}", model.countSelected)
    }

    void onWindowShown(String name, Stage window) {
        log.debug("in On Window shown with window {}", window)
        if (!initialized) {
            initialized = true
            model.timeRequired.addListener({ ObservableValue value, oldValue, newValue ->
                if (newValue.length() > 0) {
                    try {
                        model.timeRequiredDecoded = new BigDecimal(newValue)
                        model.messages.set("")
                    } catch (NumberFormatException e) {
                        model.timeRequiredDecoded = new BigDecimal(0.0)
                        model.messages.set("Time required must be numeric to the nearest tenth of an hour")
                    }
                }
            } as javafx.beans.value.ChangeListener)
         /*   model.countSelected.addListener( { ObservableValue value, oldValue, IntegerProperty newValue ->
                if (newValue.value == 0) {
                    model.closeSelected.set(true)
                } else {
                    model.closeSelected.set(false)
                }
            } as javafx.beans.value.ChangeListener)*/
        }
        if (name.equals("MaintenanceWindow")) {
            List<ReferenceItem> areas = dbService.getReferenceList("CAR_AREA")
            areas.each {
                view.carArea.items.add(new ObsReference(id: it.id, typeVal: it.typeName))
            }
            view.carArea.getSelectionModel().select(0)
            model.selectedCarArea.bind(view.carArea.getSelectionModel().selectedIndexProperty())
            model.datePerformed.set(LocalDate.now())
            dbService.getBadOrders(model.carId)

            model.currentTime.set(LocalTime.now().format(DateTimeFormatter.ofPattern("kk:mm")))
            List<BOViewModel> badOrderedCars = dbService.getBadOrders(model.carId)
            badOrderedCars.each {
                it.selected.selectedProperty().addListener(changeListener as javafx.beans.value.ChangeListener )
            }
            log.debug("the list of bad ordered cars is {}", badOrderedCars)
            view.closeSelected.disableProperty().set(true)
            runInsideUISync( {
                view.badOrderedCars.getItems().remove(0, view.badOrderedCars.getItems().size())
                log.debug("adding a bad ordered car {}", it)
                badOrderedCars.each {view.badOrderedCars.items.add(it)}
            })
        }
    }



    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void cancelAction() {
        log.debug("canceling out of the maintenance window")
        application.windowManager.hide("MaintenanceWindow")
    }

    @ControllerAction
    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    void saveEntryAction() {
        log.debug("saving the maintenance data")
        MaintenanceItem item = new MaintenanceItem()
        item.carId = model.carId
        item.carArea = view.carArea.getSelectionModel().getSelectedItem().id
        item.problemDescription = model.probDesc.get()
        item.workPerformed = model.workPerformed.get()
        if (model.closeSelected.get()) {
            item.badOrdersClosed = '1'
        } else {
            item.badOrdersClosed = '0'
        }
        item.datePerformed = java.sql.Date.valueOf(model.datePerformed.value)
        log.debug("adding the maintenance item {}", item)
        item.id = dbService.addMaintenance(item)
        if (model.closeSelected.get()) {
            BadOrderUpdateParameter boUpdate = new BadOrderUpdateParameter()
            boUpdate.maintenanceId = item.id
            boUpdate.closeDate = item.datePerformed
            view.badOrderedCars.items.each {BOViewModel  it ->
                if (it.selected.selectedProperty().get()) {
                    boUpdate.boList.add(it.problemNumber)
                }
            }
            log.debug("updating the relevant bad orders")
            dbService.updateBadOrders(boUpdate)
        }
        application.windowManager.hide("MaintenanceWindow")
    }

}