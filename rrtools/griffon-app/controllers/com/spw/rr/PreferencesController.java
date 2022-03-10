package com.spw.rr;

import griffon.core.RunnableWithArgs;
import griffon.core.artifact.GriffonController;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;

@ArtifactProviderFor(GriffonController.class)
public class PreferencesController extends AbstractGriffonController {

    @Inject
    PreferencesModel model;

    @Inject
    SerialDataService dataService;

    @Inject
    PropertyService propertyService;

    private static final Logger log = LoggerFactory.getLogger(PreferencesController.class);


    private void populateValues() {
        log.debug("reading the old properties to get values");
        model.setCommPortList(dataService.getSerialPortList());
        model.setSelectedComPort(propertyService.getSavedComPort());
        model.setScaleRatio(propertyService.getSavedScaleRatio());
        model.setInspectionEvery(propertyService.getSavedInspectFreq());
        model.setInspectionSelectedUnits(propertyService.getSavedInspectUnits());
        model.setDbPassword(propertyService.getDbPassword());
        model.setDbURL(propertyService.getDbURL());
        model.setDbUsername(propertyService.getDbUsername());
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


}
