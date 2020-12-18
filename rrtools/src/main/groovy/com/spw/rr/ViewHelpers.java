package com.spw.rr;

import com.spw.rr.model.ObsReference;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ViewHelpers {
    public static void cellHelper(ComboBox<ObsReference> combo) {
        combo.setCellFactory(new Callback<ListView<ObsReference>, ListCell<ObsReference>>() {
                                 @Override
                                 public ListCell<ObsReference> call(ListView<ObsReference> param) {
                                     return new ListCellElement();
                                 }
                             }

        );
    }
    ;
}

