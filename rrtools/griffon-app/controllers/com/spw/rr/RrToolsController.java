package com.spw.rr;

import com.spw.rr.model.TableData;
import com.spw.rr.model.ViewCar;
import com.spw.rr.parameter.ViewParameter;
import griffon.core.artifact.GriffonController;
import griffon.core.controller.ControllerAction;
import griffon.core.mvc.MVCGroup;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;
import org.apache.ibatis.session.Configuration;
import griffon.transform.Threading;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@ArtifactProviderFor(GriffonController.class)
public class RrToolsController extends AbstractGriffonController {

    @Inject
    RrToolsModel model;

    private static final Logger log = LoggerFactory.getLogger(RrToolsController.class);

    private MVCGroup prefsGroup = null;

    @Inject
    PropertyService propertyService;

    @Inject
    SerialDataService dataService;

    @Inject
    DbInfoService dbInfoService;

    @Inject
    DBService dbService;

    @MVCMember
    public void setModel(@Nonnull RrToolsModel model) {
        this.model = model;
    }

    private void refreshView() {
        log.debug("rebuilding the list of cars in the current view");
        ViewParameter viewSelection = new ViewParameter();
        List<ViewCar> theList = dbService.listViewCars(viewSelection);
        runInsideUIAsync(() -> {
            model.tableData.clear();
            for (ViewCar viewCar : theList) {
                TableData item = new TableData();
                item.setReportingMark(viewCar.getReportingMark());
                item.setCarNumber(viewCar.getCarNumber());
                item.setCarType(viewCar.getCarType());
                item.setAarType(viewCar.getAarType());
                model.tableData.add(item);
            }
        });
    }

    private static final String HELP_RESOURCE = "html5/index.html";
    private boolean preferencesGood = false;
    private static boolean inited = false;

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
        if (prefsGroup != null) {
            application.getWindowManager().hide("prefs");
            prefsGroup.destroy();
        }
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

    public void onShutdownStart() {
        log.debug("shutting down now");
    }

    public void onWindowShown(String name, java.awt.Window window) {
        if (name.equals("mainWindow")) {
            if (!preferencesGood) {
                preferences();
            } else {
                if (!inited) {
                    inited = true;
                    String comPort = propertyService.getSavedComPort();
                    if (!comPort.isEmpty()) {
                        log.debug("Setting comport to {}", comPort);
                        dataService.setCommPort(comPort);
                    }
                }
            }
            // add the mapper
            refreshView();
        }
    }

    @Override
     public void mvcGroupInit(Map<String, Object> args) {
        log.error("in the init method");
        if (propertyService == null) {
            log.error("property service is null");
        }
        boolean foundProperties = propertyService.loadValues();
        if (!foundProperties) {
            log.error("Properties were not found - need to go to preferences");
            preferencesGood = false;
        } else {
            preferencesGood = dbInfoService.getDbProperties();
        }
        if (preferencesGood) {

        } else {

        }
    }

    @ControllerAction
    @Threading(Threading.Policy.OUTSIDE_UITHREAD)
    public void help() {
        log.debug("showing the Help in a Browser window");
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
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