package com.spw.rr.model;

import com.spw.rr.RrToolsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
    private RrToolsModel model;

    protected static final int REPORTING_MARK = 0;
    protected static final int CAR_NUMBER = 1;
    protected static final int CAR_TYPE = 2;
    protected static final int AAR_TYPE = 3;
    private Logger log = LoggerFactory.getLogger(com.spw.rr.model.TableModel.class);


    @Override
    public int getRowCount() {
        return model.tableData.size();
    }

    @Override
    public int getColumnCount() {
        return model.tableColumns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case REPORTING_MARK:
                return model.tableData.get(rowIndex).getReportingMark();
            case CAR_NUMBER:
                return model.tableData.get(rowIndex).getCarNumber();
            case CAR_TYPE:
                return model.tableData.get(rowIndex).getCarType();
            case AAR_TYPE:
                return model.tableData.get(rowIndex).getAarType();
            default:
                log.error("invalid column number passed {}", columnIndex);
                return -1;
        }
    }

    @Override
    public Class getColumnClass(int col) {
        return String.class;
    }

    @Override
    public String getColumnName(int col) {
        return model.tableColumns.get(col);
    }

    public TableModel(RrToolsModel model) {
        this.model = model;
    }

    public TableModel() {
        this.model = null;
    }
}
