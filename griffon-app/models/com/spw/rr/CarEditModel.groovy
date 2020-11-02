package com.spw.rr

import com.spw.rr.model.ObsReference
import com.spw.rr.model.ObservableRefItem
import com.spw.rr.model.RRCar
import com.spw.rr.model.ReferenceItem
import griffon.core.artifact.GriffonModel
import griffon.transform.FXObservable
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.beans.value.ObservableValueBase
import javafx.collections.ObservableList

@ArtifactProviderFor(GriffonModel)
class CarEditModel {

    Integer carId           // id of car being edited, or null if new car
    RRCar car
    String  windowTitle
    String newTag = null
    boolean newCar = true
    SimpleIntegerProperty obsWeightUnits = new SimpleIntegerProperty(0)
    SimpleIntegerProperty obsLengthUnits = new SimpleIntegerProperty(0)
    BigDecimal carWeightDecoded = new BigDecimal(0.0)
    BigDecimal carLengthDecoded = new BigDecimal(0.0)
    BigDecimal purchasePriceDecoded = new BigDecimal(0.00)
    int currentUnitsWeight = 0
    int currentUnitsLength = 0
    SimpleStringProperty carTag = new SimpleStringProperty("")
    ObservableList<ObsReference> reportingMark
    ObservableList<ObsReference> aarType
    ObservableList<ObsReference> carType
    ObservableList<ObsReference> prrType
    ObservableList<ObsReference> couplerType
    ObservableList<ObsReference> kitType
    ObservableList<ObsReference> manufacturer
    ObservableList<ObsReference> source
    SimpleBooleanProperty weathered = new SimpleBooleanProperty(false)
    SimpleBooleanProperty resistWheels = new SimpleBooleanProperty(false)
    SimpleStringProperty bltDate = new SimpleStringProperty("")
    SimpleStringProperty carLength = new SimpleStringProperty("")
    SimpleStringProperty carWeight = new SimpleStringProperty("")
    SimpleStringProperty carWheels = new SimpleStringProperty("")
    SimpleStringProperty carColor = new SimpleStringProperty("")
    SimpleStringProperty carNumber = new SimpleStringProperty("")
    SimpleStringProperty carDescription = new SimpleStringProperty("")
    SimpleStringProperty messageText = new SimpleStringProperty("")
    SimpleStringProperty purchasePrice = new SimpleStringProperty("")
}