package com.spw.rr;

import com.spw.rr.models.TableData;
import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

@ArtifactProviderFor(GriffonModel.class)
public class RrToolsModel extends AbstractGriffonModel {

    private boolean itemSelected = false;

    public boolean getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(boolean selected) {
       firePropertyChange("selected", itemSelected, itemSelected = selected);
    }

    public ArrayList<TableData> tableData = new ArrayList<>();
    public ArrayList<String > tableColumns = new ArrayList<>(Arrays.asList("Reporting Mark", "Car Number", "Car Type", "AAR Type"));
}