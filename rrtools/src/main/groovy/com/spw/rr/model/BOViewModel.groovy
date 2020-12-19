package com.spw.rr.model

import groovy.transform.ToString
import javafx.scene.control.CheckBox

@ToString(includeNames = true, includePackage = false, includeFields = true)
class BOViewModel {
    String reportingMark
    String carNumber
    String date_entered
    CheckBox selected = new CheckBox()
    String carArea
    String problemDescription
    int problemNumber
}
