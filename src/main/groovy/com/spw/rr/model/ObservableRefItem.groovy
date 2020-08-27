package com.spw.rr.model

import groovy.transform.ToString
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

@ToString(includePackage =  false, includeFields = true)
class ObservableRefItem {
    String tableName
    SimpleIntegerProperty id
    SimpleStringProperty typeName
    SimpleStringProperty  typeDescription

    public ObservableRefItem() {

    }

    public ObservableRefItem(Integer newId, String newType, String newDescription) {
        id = new SimpleIntegerProperty(newId)
        typeName = new SimpleStringProperty(newType)
        typeDescription = new SimpleStringProperty(newDescription)
    }

    public ObservableRefItem(Map newItems) {
        tableName = newItems['tableName']
        id = new SimpleIntegerProperty(newItems['id'])
        typeName = new SimpleStringProperty(newItems['typeName'])
        typeDescription = new SimpleStringProperty(newItems['typeDescription'])
    }

}
