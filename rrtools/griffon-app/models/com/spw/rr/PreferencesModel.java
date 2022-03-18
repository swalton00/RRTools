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

    @Inject
    DbInfoService dbInfo;

    // data elements to be saved to properties
    protected enum DbDefType {NONE, URL, NAME}
    DbDefType dbDefType;
    String selectedComPort;
    String scaleRatio = "";
    String scaleName = "";
    String selectedUnitSystem = "";
    String inspectionEvery = "";
    String inspectionSelectedUnits = "";
    String dbName = "";
    String dbLocation = "";
    String dbURL = "";
    String dbUsername = "";
    String dbPassword = "";
    Boolean modedDbName = false;
    Boolean modedDbLocation = false;
    Boolean modedDbURL = false;
    Boolean modedDbUsername = false;
    Boolean modedDbPassword = false;
    // end of properties elements
    // start of ui elements
    ArrayList<String> commPorts;
    protected String[] inspectionUnits = {"Months", "Years"};
    protected String[] unitSystems = {"English", "Metric"};
    protected String[] scales = {"Z", "N", "HO", "S", "O", "G"};
    protected String[]inspectionFrequency = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String message = "";
    // UI elements

    // original values
    String oldComPort;
    String oldScaleRatio;
    String oldScaleName;
    String oldInspectionEvery;
    String oldInspectionUnits;
    String oldDbName;
    String oldDbUser;
    String oldDbUrl;
    String oldDbPass;
    String oldDbLocation;
    // end of original

    // do we require a change?
    boolean changeRequired = false;

    public void init() {
        log.debug("initializing the Preferences Model");
        commPorts = dataService.getSerialPortList();
        copyFromProperties();
        dbInfo.setDatabaseInfo(dbURL, dbUsername, dbPassword);
        dbLocation = dbInfo.getDbLocation();
        dbName = dbInfo.getDatabaseName();
        if (dbInfo.urlRequired()) {
            dbDefType = dbDefType.URL;
        } else if (dbURL.isEmpty()) {
            dbDefType = dbDefType.NAME;
        } else {
            dbDefType = dbDefType.NONE;
        }
    }

    private void copyFromProperties() {
        selectedComPort = propertyService.getSavedComPort();
        setScaleRatio(propertyService.getSavedScaleRatio());
        setInspectionEvery(propertyService.getSavedInspectFreq());
        inspectionSelectedUnits = propertyService.getSavedInspectUnits();
        dbPassword =propertyService.getDbPassword();
        dbURL = propertyService.getDbURL();
        dbUsername = propertyService.getDbUsername();
        selectedUnitSystem = unitSystems[propertyService.getSavedUnits()];
        scaleName = propertyService.getSavedScaleName();
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

    public Boolean getModedDbName() {
        return modedDbName;
    }

    public Boolean getModedDbLocation() {
        return modedDbLocation;
    }

    public Boolean getModedDbURL() {
        return modedDbURL;
    }

    public Boolean getModedDbUsername() {
        return modedDbUsername;
    }

    public Boolean getModedDbPassword() {
        return modedDbPassword;
    }
}
