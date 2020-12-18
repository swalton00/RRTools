package com.spw.rr

import com.spw.rr.model.BadOrder
import com.spw.rr.model.ObsReference
import com.spw.rr.model.ReferenceItem
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.stage.Stage

import javax.annotation.Nonnull
import javax.inject.Inject
import java.time.LocalDate

@ArtifactProviderFor(GriffonController)
class BadOrderController {
    @MVCMember @Nonnull
    BadOrderModel model

    @MVCMember
    BadOrderView view

    @Inject
    DBService dbService

    boolean initialized = false

    private void initData() {
        view.areaOfCar.getSelectionModel().select(0)
        model.dateReported.value = LocalDate.now()
        model.outOfService.set(true)
        model.problemDescription.set("")
    }

    void onWindowShown(String name, Stage window) {
        log.debug("Bad Order - in On Window shown with window {}", name)
        if (name.equals("BadOrderWindow")) {
            if (!initialized) {
                log.debug("intializing the bad order window")
                initialized = true
                List<ReferenceItem> areas = dbService.getReferenceList("CAR_AREA")
                areas.each {
                    view.areaOfCar.items.add(new ObsReference(id: it.id, typeVal: it.typeName))
                }
                view.areaOfCar.getSelectionModel().select(0)
                model.areaChosen.bind(view.areaOfCar.getSelectionModel().selectedIndexProperty())
            }
            initData()
        }
    }

        @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void cancelBadOrderAction() {
        log.debug("canceling out of the bad order window")
        application.windowManager.hide("BadOrderWindow")

    }

    @ControllerAction
    @Threading(Threading.Policy.OUTSIDE_UITHREAD_ASYNC)
    void createBadOrderAction() {
        log.debug("creating the bad order record")
        BadOrder badOrder = new BadOrder()
        badOrder.carId = model.carId
        badOrder.entryDate = java.sql.Date.valueOf(model.dateReported.value)
        badOrder.problemArea = view.areaOfCar.getSelectionModel().getSelectedItem().id
        if (model.outOfService.value) {
            badOrder.outOfService = "1"
        } else {
            badOrder.outOfService = "0"
        }
        badOrder.inEffect = "1"
        badOrder.problemDescription = model.problemDescription.value
        log.debug("about to create the following bad Order record {}", badOrder )
        dbService.addBadOrder(badOrder)
        application.windowManager.hide("BadOrderWindow")

    }

}