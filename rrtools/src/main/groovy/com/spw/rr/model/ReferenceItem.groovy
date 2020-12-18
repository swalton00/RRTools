package com.spw.rr.model

import groovy.transform.ToString


@ToString(includePackage = false, includeFields = true)
class ReferenceItem {
    String tableName
    Integer id
    String typeName
    String typeDescription

    String getTableName() {
        return tableName
    }

    void setTableName(String tableName) {
        this.tableName = tableName
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = new Integer((int)id)
    }

    String getTypeName() {
        return typeName
    }

    void setTypeName(String typeName) {
        this.typeName = typeName
    }

    String getTypeDescription() {
        return typeDescription
    }

    void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription
    }
}
