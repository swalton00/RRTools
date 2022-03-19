package com.spw.rr;

import griffon.core.RunnableWithArgs;
import griffon.core.artifact.GriffonController;
import griffon.metadata.ArtifactProviderFor;
import griffon.transform.Threading;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

@ArtifactProviderFor(GriffonController.class)
public class PreferencesController extends AbstractGriffonController {

    @Inject
    PreferencesModel model;

    @Inject
    SerialDataService dataService;

    @Inject
    PropertyService propertyService;

    @Inject
    PreferencesView view;

    @Inject
    DbInfoService dbInfo;

    private static final Logger log = LoggerFactory.getLogger(PreferencesController.class);

    public void okayAction() {
        log.debug("saving the preferences");
        if (!model.oldComPort.equals(model.selectedComPort)) {
            dataService.setCommPort(model.selectedComPort);
            propertyService.setSavedComPort(model.selectedComPort);
        }
        if (!model.oldInspectionEvery.equals(model.inspectionEvery)) {
            propertyService.setSavedInspectFreq(Integer.valueOf(model.inspectionEvery));
        }
        if (!model.inspectionSelectedUnits.equals(model.oldInspectionUnits)) {
            propertyService.setSavedInspectUnits(model.inspectionSelectedUnits);
        }
        if (!model.oldScaleName.equals(model.scaleName)) {
            propertyService.setSavedScaleName(model.scaleName);
        }
        if (!model.oldScaleRatio.equals(model.scaleRatio)) {
            propertyService.setSavedScaleRatio(Integer.valueOf(model.scaleRatio));
        }
        if (model.modedDbUsername || model.modedDbPassword || model.getModedDbURL() || model.modedDbLocation || model.modedDbLocation) {
            // some database information changed - verify and restart if good
        }
        String testUrl;
        if (  model.dbDefType == PreferencesModel.DbDefType.NAME) {
            String newUrl = dbInfo.createUrl(model.dbName, model.dbLocation);
            if ( newUrl == null) {
                view.setErrorMessage("Verification failed for database name and location");
                return;
            }
            testUrl = newUrl;
        } else {
            testUrl = model.dbURL;
        }
        String testResults = dbInfo.testUrl(testUrl);
        if (!testResults.isEmpty()) {
            view.setErrorMessage(testResults);
        }
        boolean goodValues = dbInfo.testDbData(testUrl, model.dbName, model.dbPassword);
        if (goodValues) {
            view.setMessage("");
            propertyService.setDbURL(testUrl);
            propertyService.setDbUsername(model.dbUsername);
            propertyService.setDbPassword(model.dbPassword);
            model.changeRequired = false;
            propertyService.saveProperties();
            Object[] options = {"OK", "Cancel"};
            JOptionPane.showConfirmDialog(view.window, "Database Elements have changed - Press Okay to restart");
            application.shutdown();
        }
        if (!model.changeRequired) {
            application.getWindowManager().hide("prefs");
        }
    }

    public void cancelAction() {
        log.debug("canceling out of the preferences window");
        if (model.changeRequired) {
            log.error("cancel requested but information is required");
            view.setErrorMessage("Cancel was requested but information is required");
            return;
        }
        application.getWindowManager().hide("prefs");
        // restore values in case we get invoked again
        model.selectedComPort = model.oldComPort;
        model.scaleRatio = model.oldScaleRatio;
        model.inspectionEvery = model.oldInspectionEvery;
        model.inspectionSelectedUnits = model.oldInspectionUnits;
        if (!(model.modedDbLocation && model.modedDbName && model.modedDbUsername && model.getModedDbPassword() && model.getModedDbURL())) {
            log.debug("no database changes -- no need to restore values");
            return;
        }
        model.dbLocation = model.oldDbLocation;
        model.dbName = model.oldDbName;
        model.dbUsername = model.oldDbUser;
        model.dbPassword = model.oldDbPass;
        model.dbLocation = model.oldDbLocation;
        model.modedDbURL = false;
        model.modedDbUsername = false;
        model.modedDbLocation = false;
        model.modedDbName = false;
    }

    public void helpAction() {
        log.debug("help was requested");
    }

    public void onWindowShown(String name, Window window) {
        if (name != "prefs") {
            return;
        }
        log.debug("setting up the preferences");
        model.oldComPort = model.selectedComPort;
        model.oldScaleRatio = model.scaleRatio;
        model.oldScaleName = model.scaleName;
        model.oldInspectionEvery = model.inspectionEvery;
        model.oldInspectionUnits = model.inspectionSelectedUnits;
        model.oldDbName = model.dbName;
        model.oldDbUser = model.dbUsername;
        model.oldDbPass = model.dbPassword;
        model.oldDbLocation = model.dbLocation;
        if (!dbInfo.getDbProperties()) {
            model.changeRequired = true;
        }
    }

    @Threading(Threading.Policy.INSIDE_UITHREAD_SYNC)
    public void location() {
        log.debug("setting the database location");
        JFileChooser dbLocation = null;
        if (model.dbLocation.isEmpty()) {
            dbLocation = new JFileChooser();

        } else {
            dbLocation = new JFileChooser(new File(model.dbLocation));
        }
        dbLocation.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = dbLocation.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            model.dbLocation = dbLocation.getSelectedFile().getName();
        }
    }

    public void onShutdownStart() {
        log.debug("closing out the Preferences group");

    }

    @Override
    public void mvcGroupInit(Map<String, Object> args) {
        log.debug("Ready to run -- checking for PropertyService");
        if (propertyService == null) {
            log.error("property service is null");
        }
    }



}
