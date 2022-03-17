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

    private static final Logger log = LoggerFactory.getLogger(PreferencesController.class);

    public void okayAction() {
        log.debug("saving the preferences");
        application.getWindowManager().hide("prefs");
    }

    public void cancelAction() {
        log.debug("canceling out of the preferences window");
        application.getWindowManager().hide("prefs");
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
