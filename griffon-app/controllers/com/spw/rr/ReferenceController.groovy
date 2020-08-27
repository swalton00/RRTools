package com.spw.rr

import com.spw.rr.model.ObservableRefItem
import com.spw.rr.model.ReferenceItem
import com.sun.xml.internal.ws.util.InjectionPlan
import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
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

    void onWindowShown(String name, Stage window) {
        log.debug("in the window shown method for Reference")
        view.typeColumn.textProperty().set(model.columnName)
        if (name.equals("referenceWindow")) {
            window.setTitle(model.windowTitle)
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
                log.debug("adding the temporary list to the TableView element")
                tempList.each { refItem->
                    view.refTableView.getItems().add(createRefItem(refItem))
                    log.debug("adding item {} to the TableView", refItem)
                }
            })
        }
    }

    ObservableRefItem createRefItem(ReferenceItem item) {
        return new ObservableRefItem(tableName: model.referenceTable,
                id:item.id,
                typeName: item.typeName,
                typeDescription: item.typeDescription)
    }

    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    void refCancelAction() {
        log.debug("Canceling out of the Reference Window")
        hideWindow()
    }

    @ControllerAction
    void refSaveAction() {
        log.debug("Saving the reference data")
        //TODO: add the save db code
        hideWindow()
    }
}