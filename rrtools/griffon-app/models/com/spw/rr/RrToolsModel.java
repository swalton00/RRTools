package com.spw.rr;

import com.spw.rr.models.TableData;
import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

@ArtifactProviderFor(GriffonModel.class)
public class RrToolsModel extends AbstractGriffonModel {
    private static final Logger log = LoggerFactory.getLogger(RrToolsModel.class);
    private boolean itemSelected = false;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        firePropertyChange("message", this.message, message);
        this.message = message;
    }

    private String message = "";

    public boolean getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(boolean selected) {
       firePropertyChange("selected", itemSelected, itemSelected = selected);
    }

    public ArrayList<TableData> tableData = new ArrayList<>();
    public ArrayList<String > tableColumns = new ArrayList<>(Arrays.asList("Reporting Mark", "Car Number", "Car Type", "AAR Type"));
}