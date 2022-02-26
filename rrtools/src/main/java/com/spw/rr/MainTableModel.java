package com.spw.rr;

import javax.swing.table.AbstractTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTableModel extends AbstractTableModel {

    final Logger log = LoggerFactory.getLogger(MainTableModel.class);
    private RrToolsModel model;

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    public MainTableModel(RrToolsModel model) {
        super();
        this.model = model;
    }

    public MainTableModel() {
        super();
    }
}
