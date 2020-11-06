package com.spw.rr.model

class ObsReference {
    Integer id
    String typeVal
    String description

    public ObsReference() {

    }

    public ObsReference(String val) {
        typeVal = val
        id = null
    }

    public void setTypeVal(String newVal) {
        typeVal = newVal
    }

    public String getTypeVal() {
        return typeVal
    }



    String toString() {
        return typeVal
    }

}
