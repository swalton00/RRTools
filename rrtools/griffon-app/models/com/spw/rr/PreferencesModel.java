package com.spw.rr;

import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;

@ArtifactProviderFor(GriffonModel.class)
public class PreferencesModel extends AbstractGriffonModel {

    private static final Logger log = LoggerFactory.getLogger(PreferencesModel.class);

    @Inject
    PropertyService propertyService;

    @Inject
    SerialDataService dataService;


    // data elements to be saved to properties
    String scaleRatio = "";
    String scaleName = "";
    String unitSystem = "";
    String inspectionEvery = "";
    String inspectionSelectedUnits = "";
    String dbUsername = "";
    String dbPassword = "";
    String dbName = "";
    String dbURL = "";
    String selectedComPort;
    // end of properties elements
    // start of ui elements
    ArrayList<String> commPorts;
    protected String[] inspectionUnits = {"Months", "Years"};
    protected String[] unitSystems = {"English", "Metric"};
    protected String[] scales = {"Z", "N", "HO", "S", "O", "G"};
    protected String[]inspectionFrequency = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String message = "";

    public void init() {
        log.debug("initializing the Preferences Model");
        commPorts = dataService.getSerialPortList();
        copyFromProperties();
    }

    private void copyFromProperties() {
        setSelectedComPort(propertyService.getSavedComPort());
        setScaleRatio(propertyService.getSavedScaleRatio());
        setInspectionEvery(propertyService.getSavedInspectFreq());
        setInspectionSelectedUnits(propertyService.getSavedInspectUnits());
        setDbPassword(propertyService.getDbPassword());
        setDbURL(propertyService.getDbURL());
        setDbUsername(propertyService.getDbUsername());
    }

    public String getScaleRatio() {
        return scaleRatio;
    }

    public void setScaleRatio(Integer scaleRatio) {
        this.scaleRatio = scaleRatio.toString();
    }

    public String getInspectionEvery() {
        return inspectionEvery;
    }

    public void setInspectionEvery(Integer inspectionEvery) {
        this.inspectionEvery = inspectionEvery.toString();
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbURL() {
        return dbURL;
    }

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSelectedComPort() {
        return selectedComPort;
    }

    public void setSelectedComPort(String selectedComPort) {
        this.selectedComPort = selectedComPort;
    }

    public String getInspectionSelectedUnits() {
        return inspectionSelectedUnits;
    }

    public void setInspectionSelectedUnits(String inspectionSelectedUnits) {
        this.inspectionSelectedUnits = inspectionSelectedUnits;
    }


}
