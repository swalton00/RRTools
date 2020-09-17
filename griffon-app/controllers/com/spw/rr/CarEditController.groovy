package com.spw.rr

import com.spw.rr.model.ObsReference
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
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

    void onWindowShown(String name, Stage window) {
        log.debug("in the window shown method for CarEdit")
        if (name.equals("carEditWindow")) {
            model.carTag.setValue("")
            doChoice(view.carType, "CAR_TYPE", model.carType)
            doChoice(view.carAARType, "AAR_TYPE", model.aarType)
            doChoice(view.carKitType, "KIT_TYPE", model.kitType)
            doChoice(view.carCouplerType, "COUPLER_TYPE", model.couplerType)
            doChoice(view.carTruckType, "TRUCK_TYPE", model.truckType)

        }
    }

    public void onTag_Read(String newTag) {
        log.debug("got a tag of {} ", newTag)
        runInsideUIAsync {
            model.carTag.setValue(newTag)
        }

    }


    private closeWindow() {
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


    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void cancelChangesAction() {
        log.debug("canceling any changes and closing the car edit window")
        closeWindow()
    }

    @ControllerAction
    void saveChangesAction() {
        log.debug("saving the changes made to the car")
        //TODO: add the save code
        closeWindow()
    }

}