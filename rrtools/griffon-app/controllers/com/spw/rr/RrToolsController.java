package com.spw.rr;

import griffon.core.artifact.GriffonController;
import griffon.core.controller.ControllerAction;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import griffon.transform.Threading;
import javax.annotation.Nonnull;

@ArtifactProviderFor(GriffonController.class)
public class RrToolsController extends AbstractGriffonController {
    private RrToolsModel model;

    @MVCMember
    public void setModel(@Nonnull RrToolsModel model) {
        this.model = model;
    }
    @ControllerAction
    @Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
    public void export() {
        application.getLog().error("export invoked");
    }

    public void importAction() {

    }

    public void backup() {

    }

    public void restore() {

    }

    public void close() {
        //application.getlog().debug("shutting down now");
        getApplication().shutdown();
    }

    public void edit() {

    }

    public void inspect() {

    }

    public void delete() {

    }

    public void maintain() {

    }

    public void badOrder() {

    }

    public void preferences() {

    }

    public void carType() {

    }

    public void aarType() {

    }

    public void kitType() {

    }

    public void prrType() {

    }

    public void exportTableData() {

    }

    public void importTableData() {

    }

    public void viewAll() {

    }

    public void viewMaintenance() {

    }

    public void viewInspectNeeded() {

    }

    public void viewMissingTags() {

    }

    public void viewByRoadName() {

    }

    public void help() {

    }
}