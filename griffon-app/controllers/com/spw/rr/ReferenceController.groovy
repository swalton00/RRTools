package com.spw.rr

import com.spw.rr.model.ObservableRefItem
import com.spw.rr.model.ReferenceItem
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.input.KeyEvent
import javafx.stage.Stage
import javax.inject.Inject

import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonController)
class ReferenceController {
    @MVCMember
    @Nonnull
    ReferenceModel model

    @MVCMember
    ReferenceView view

    @Inject
    DBService dbService

    private boolean inited = false
    private List tempList = new ArrayList<ReferenceItem>()

    private void hideWindow() {
        log.debug("Hiding the Reference window")
        application.windowManager.hide("referenceWindow")
    }

    private boolean typedData(obs, oldVal, newVal, thisChanged, thatChanged) {
        if (newVal) {
            thisChanged = true
        } else {
            thisChanged = false
        }
        if (thisChanged && thatChanged)
            model.refEnterNotReady.setValue(false)
        else
            model.refEnterNotReady.setValue(true)
        return thisChanged
    }


    void onWindowShown(String name, Stage window) {
        log.debug("in the window shown method for Reference")
        view.typeColumn.textProperty().set(model.columnName)
        if (name.equals("referenceWindow")) {
            /*model.newTypeValue.addListener(new ChangeListener() {
                @Override
                void changed(ObservableValue obs, Object oldVal, Object newVal) {
                    model.newTypedEntered = typedData(obs, oldVal, newVal, model.newTypedEntered, model.newDescriptionEntered)
                }
            })
            model.newDescriptionValue.addListener(new ChangeListener<String>() {
                @Override
                void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    model.newDescriptionEntered = typedData(observable, oldValue, newValue, model.newDescriptionEntered, model.newTypedEntered)
                }
            })*/
            window.setTitle(model.windowTitle)
            model.refDataClean.setValue(true)
            model.newDescriptionEntered.setValue(false)
            model.newTypedEntered.setValue(false)
            model.newDescriptionValue.setValue("")
            model.newTypeValue.setValue("")

            List tempList = new ArrayList<ReferenceItem>()
            List referenceList = dbService.getReferenceList(model.referenceTable)
            log.debug("got back a list which has {} elements in it ", referenceList.size())
            referenceList.each {
                log.debug("adding a row {}", it)
                tempList.add(it)
            }
            log.debug("TempList now has {} rows", tempList.size())
            runInsideUISync({
                model.tableData.removeAll()
                view.refTableView.getItems().remove(0, view.refTableView.getItems().size())
                log.debug("adding the temporary list to the TableView element")
                tempList.each { refItem ->
                    view.refTableView.getItems().add(createRefItem(refItem))
                    log.debug("adding item {} to the TableView", refItem)
                }
                view.refTableView.getSelectionModel().clearSelection()
            })
        }
    }

    ObservableRefItem createRefItem(ReferenceItem item) {
        return new ObservableRefItem(tableName: model.referenceTable,
                id: item.id,
                typeName: item.typeName,
                typeDescription: item.typeDescription)
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void onRefTextValueAction() {
        log.debug("Text being entered into Ref Text Value")
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void refCancelAction() {
        log.debug("Canceling out of the Reference Window")
        hideWindow()
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    refAddNewItemAction() {
        log.debug("adding a new item")
        ObservableRefItem newItem = new ObservableRefItem(typeName:  model.newTypeValue.getValue(),
                                                           typeDescription: model.newDescriptionValue.getValue())
        view.refTableView.getItems().add(newItem)
        model.refDataClean.set(false)
        view.refNewType.setText("")
        view.refNewDescription.setText("")
    }

    @ControllerAction
    void refSaveAction() {
        log.debug("Saving the reference data")
        if (!(model.refDataClean.value)) {
            List dataList = view.refTableView.getItems()
            dataList.each { item ->
                ReferenceItem ref = new ReferenceItem(typeName: item.getTypeName(),
                        typeDescription: item.getTypeDescription())
                ref.id = item.getId()
                ref.tableName = model.referenceTable
                if (ref.id == null) {
                    log.debug("adding new reference item {}", item)
                    dbService.addReferenceItem(ref, model.referenceTable)
                } else {
                    if (item.dirty) {
                        log.debug("updating the reference item {}", ref)
                        dbService.saveReferenceItem(ref, model.referenceTable)
                    }
                }
            }
        }
        hideWindow()
    }
}