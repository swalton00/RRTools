package com.spw.rr.model

import groovy.transform.ToString
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue

@ToString(includePackage = false, includeFields = true)
class ObservableRefItem {
    boolean dirty = false
    String tableName

    String getRfidTag() {
        return rfidTag
    }

    void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag
    }
    String rfidTag
    SimpleIntegerProperty id = new SimpleIntegerProperty()
    SimpleStringProperty typeName = new SimpleStringProperty()
    SimpleStringProperty typeDescription = new SimpleStringProperty()

    def listener = new ChangeListener()  {

        @Override
        void changed(ObservableValue observable, Object oldValue, Object newValue) {
            if (!oldValue.equals(newValue)) {
                dirty = true
            }

        }
    }

    private void addRequiredListeners() {
        typeName.addListener(listener)
        typeDescription.addListener(listener)
    }

    public ObservableRefItem() {
        addRequiredListeners()
    }

    public ObservableRefItem(Integer newId, String newType, String newDescription) {
        id.set(newId)
        typeName.set(newType)
        typeDescription.set(newDescription)
        addRequiredListeners()
    }

    public ObservableRefItem(Map newItems) {
        tableName = newItems['tableName']
        if (newItems['id'] != null) {
            id.set(newItems['id'])
        }
        typeName.set(newItems['typeName'])
        typeDescription.set(newItems['typeDescription'])
        rfidTag = newItems['rfidTag']
        addRequiredListeners()
    }

    public Integer getId() {
        return id.get()
    }

    public void setId(Integer intVal) {
        id.set(intVal)
    }

    public String getTypeName() {
        return typeName.get()
    }

    public void setTypeName(String nameVal) {
        typeName.set(nameVal)
    }

    public String getTypeDescription() {
        typeDescription.get()
    }

    public void setTypeDescription(String descVal) {
        typeDescription.set(descVal)
    }

}
