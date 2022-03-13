package com.spw.rr;

import griffon.core.RunnableWithArgs;
import griffon.core.artifact.GriffonController;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Map;

@ArtifactProviderFor(GriffonController.class)
public class PreferencesController extends AbstractGriffonController {

    @Inject
    PreferencesModel model;

    @Inject
    SerialDataService dataService;

    @Inject
    PropertyService propertyService;

    private static final Logger log = LoggerFactory.getLogger(PreferencesController.class);

    public void okayAction() {
        log.debug("saving the preferences");
        application.getWindowManager().hide("prefs");
    }

    public void cancelAction() {
        log.debug("canceling out of the preferences window");
        application.getWindowManager().hide("prefs");
    }


    @Override
    public void mvcGroupInit(Map<String, Object> args) {
        log.debug("Ready to run -- checking for PropertyService");
        if (propertyService == null) {
            log.error("property service is null");
        }
    }



}
