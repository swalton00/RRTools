package com.spw.rr.model;

public class ReferenceItem {
    String tableName;
    Integer id;
    String typeName;
    String typeDescription;

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("tableName: ");
        sb.append(tableName);
        sb.append(" Id: ");
        sb.append(id);
        sb.append(" typeName: ");
        sb.append(typeName);
        sb.append(" description ");
        sb.append(typeDescription);
        return sb.toString();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}
