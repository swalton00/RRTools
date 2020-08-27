package com.spw.rr.model

import groovy.transform.ToString

@ToString(includePackage = false, includeFields = true)
class ReferenceItem {
    String tableName
    Integer id
    String typeName
    String typeDescription
}
