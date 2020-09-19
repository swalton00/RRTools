package com.spw.rr;

import com.spw.rr.model.ObsReference;
import javafx.scene.control.ListCell;

public class ListCellElement extends ListCell<ObsReference> {
    @Override
    protected void updateItem(ObsReference item, boolean empty) {
        super.updateItem(item, empty);
        setText(item == null ? "" : item.getTypeVal());
    }
}