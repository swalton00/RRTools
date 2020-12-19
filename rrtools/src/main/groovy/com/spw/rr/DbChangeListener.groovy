package com.spw.rr

import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Singleton
class DbChangeListener implements ChangeListener {

    Boolean parentsChangedValue = false
    private static final Logger log = LoggerFactory.getLogger(DbChangeListener.class);


    @Override
    void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (oldValue.equals(newValue))
            return
        log.debug("DbChangeListener setting changed value to true")
        parentsChangedValue = true
    }

    boolean getChanged() {
        return parentsChangedValue
    }

    void setChanged(Boolean changed) {
        parentsChangedValue = changed
    }

    void setChangedValue(boolean changedValue) {
        parentsChangedValue = changedValue
        log.debug("set DbChangedValue to {}", changedValue)
    }
}
