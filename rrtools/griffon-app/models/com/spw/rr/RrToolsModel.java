package com.spw.rr;

import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;

@ArtifactProviderFor(GriffonModel.class)
public class RrToolsModel extends AbstractGriffonModel {

    private boolean itemSelected = false;

    public boolean getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(boolean selected) {
       firePropertyChange("selected", itemSelected, itemSelected = selected);
    }

    String[] tableColumns = {"Reporting Mark", "Car Number", "Car Type", "AAR Type"};
}