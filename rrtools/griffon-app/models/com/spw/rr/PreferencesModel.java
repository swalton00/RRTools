package com.spw.rr;

import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@ArtifactProviderFor(GriffonModel.class)
public class PreferencesModel extends AbstractGriffonModel {

    private static final Logger log = LoggerFactory.getLogger(PreferencesModel.class);


    String scaleRatio = "";
    String inspectionEvery = "";
    String inspectionSelectedUnits = "";
    protected ArrayList<String> inspectionUnits = new ArrayList<String>();
    String dbUsername = "";
    String dbPassword = "";
    String dbURL = "";
    String message = "";
    ArrayList<String> commPorts;
    String selectedComPort;

    public PreferencesModel() {
        super();
        inspectionUnits.add("Months");
        inspectionUnits.add("Years");
    }

    public void setCommPortList(ArrayList<String> ports) {
        log.debug("Setting the com port list");
        commPorts = ports;
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
