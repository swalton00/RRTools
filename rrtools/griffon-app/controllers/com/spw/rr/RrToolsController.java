package com.spw.rr;

import griffon.core.GriffonApplication;
import griffon.core.RunnableWithArgs;
import griffon.core.artifact.GriffonController;
import griffon.core.controller.ControllerAction;
import griffon.core.mvc.MVCGroup;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import griffon.transform.Threading;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@ArtifactProviderFor(GriffonController.class)
public class RrToolsController extends AbstractGriffonController {

    @Inject
    RrToolsModel model;

    private Logger log = LoggerFactory.getLogger(RrToolsController.class);

    private MVCGroup prefsGroup = null;

    @Inject
    PropertyService propertyService;

    @Inject
    SerialDataService dataService;

    @MVCMember
    public void setModel(@Nonnull RrToolsModel model) {
        this.model = model;
    }

    private static final String HELP_RESOURCE = "html5/index.html";

    private MVCGroup getGroup(MVCGroup group, String groupName) {
        if (group != null) {
            return group;
        }
        MVCGroup retGroup = application.getMvcGroupManager().findGroup(groupName);
        if (retGroup == null) {
            return createMVCGroup(groupName);
        }
        return retGroup;
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
        log.debug("shutting down now");
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

    @ControllerAction
    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    public void preferences() {
        log.debug("Showing the preferences window");
        prefsGroup = getGroup(prefsGroup, "preferences");
        application.getWindowManager().show("prefs");
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



    RunnableWithArgs onShutdownStart = new RunnableWithArgs() {
        @Override
        public void run(@Nullable Object... args) {
            log.debug("shutting down now");
            propertyService.saveProperties();
        }

    };

    @Override
     public void mvcGroupInit(Map<String, Object> args) {
        log.error("in the init method");
        if (propertyService == null) {
            log.error("property service is null");
        }
        boolean foundProperties = propertyService.loadValues();
        log.error("Properties were not found - need to go to preferences");
    }

    RunnableWithArgs onMvcGroupInit = new RunnableWithArgs() {
        @Override
        public void run(@Nullable Object... args) {
            log.debug("Ready to run -- checking for PropertyService");
            if (propertyService == null) {
                log.error("property service is null");
            }
        }
    };

    @ControllerAction
    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    public void help() {
        log.debug("showing the Help in a Browser window");
        Desktop desktop = Desktop.getDesktop();
        try {
            URL resource = application.getResourceHandler().getResourceAsURL(HELP_RESOURCE);
            String filePath = Paths.get(resource.toURI()).toFile().getAbsolutePath();
            String fullUrl = "file://localhost/" + filePath;
            String validUrl = fullUrl.replace("\\", "/");
            log.debug("Using a URL of {}", validUrl);
            desktop.browse(URI.create(validUrl));
        } catch (Exception e) {
            log.error("Exception attempting to show help in a browser window", e);
        }
    }
}