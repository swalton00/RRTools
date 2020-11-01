package com.spw.rr

import com.spw.rr.model.ObsReference
import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ComboBox
import javafx.scene.control.DatePicker
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldListCell
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Callback
import javafx.util.StringConverter
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView
import javax.annotation.Nonnull
import javax.inject.Inject
import impl.org.controlsfx.tools.PrefixSelectionCustomizer

@ArtifactProviderFor(GriffonView)
class CarEditView extends AbstractJavaFXGriffonView {
    @MVCMember
    @Nonnull
    FactoryBuilderSupport builder
    @MVCMember
    @Nonnull
    CarEditController controller
    @MVCMember
    @Nonnull
    CarEditModel model

    @Inject
    PropertyService propertyService

    @FXML
    Label carId
    @FXML
    Label carTag
    @FXML
    ComboBox carReportingMark
    @FXML
    TextField carNumber
    @FXML
    ChoiceBox carType
    @FXML
    ChoiceBox carAARType
    @FXML
    ChoiceBox carPRRType
    @FXML
    ChoiceBox carCouplerType
    @FXML
    ChoiceBox carKitType
    @FXML
    TextField carLength
    @FXML
    TextField carWeight
    @FXML
    ChoiceBox carLengthUnits
    @FXML
    ChoiceBox carWeightUnits
    @FXML
    TextField carWheels
    @FXML
    TextField carColor
    @FXML
    DatePicker datePuchased
    @FXML
    DatePicker inServiceDate
    @FXML
    DatePicker kitBuiltDate
    @FXML
    TextField carBltDate
    @FXML
    TextArea carDescription
    @FXML Button saveChangesActionTarget
    @FXML Label messageText
    @FXML ComboBox<String> vendor
    @FXML TextField purchasePrice
    @FXML ComboBox<String> manufacturer

    void initUI() {
        Stage stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle(model.windowTitle);
        stage.initModality(Modality.APPLICATION_MODAL)
        //stage.setWidth(400);
        //stage.setHeight(600);
        stage.setScene(init());
        model.carTag.bindBidirectional(carTag.textProperty())
        carLengthUnits.setItems(FXCollections.observableArrayList("Feet", "Meters"))
        carWeightUnits.setItems(FXCollections.observableArrayList("ounces", "grams"))
        model.obsLengthUnits.set(propertyService.getUnits())
        model.obsWeightUnits.set(propertyService.getUnits())
        carLengthUnits.getSelectionModel().select(model.obsLengthUnits.get())
        carWeightUnits.getSelectionModel().select(model.obsWeightUnits.get())
        model.obsWeightUnits.bind(carWeightUnits.getSelectionModel().selectedIndexProperty())
        model.obsLengthUnits.bind(carLengthUnits.getSelectionModel().selectedIndexProperty())
        model.carDescription.bindBidirectional(carDescription.textProperty())
        model.carWheels.bindBidirectional(carWheels.textProperty())
        model.bltDate.bindBidirectional(carBltDate.textProperty())
        model.carColor.bindBidirectional(carColor.textProperty())
        model.carNumber.bindBidirectional(carNumber.textProperty())
        model.carLength.bindBidirectional(carLength.textProperty())
        model.carWeight.bindBidirectional(carWeight.textProperty())
        model.purchasePrice.bindBidirectional(purchasePrice.textProperty())
        saveChangesActionTarget.disableProperty().bind(carReportingMark.getSelectionModel().selectedItemProperty().isNull().and(
                model.carNumber.isEmpty()
        ))
        carReportingMark.setConverter(new StringConverter<ObsReference>() {
            @Override
            String toString(ObsReference object) {
                log.debug("Object is {}", object)
                return object
            }

            @Override
            ObsReference fromString(String string) {
                log.debug("From String - string is {}", string)
                return new ObsReference(string)
            }
        })
        carReportingMark.setCellFactory(ViewHelpers.cellHelper(carReportingMark))
        carReportingMark.setOnAction({ event ->
            if (carReportingMark.getValue() != null && !carReportingMark.getValue().typeVal.trim().isEmpty()) {
                String value = carReportingMark.getValue().typeVal.trim()
                ObservableList<ObsReference> items = carReportingMark.getItems()
                boolean contained = false
                int currentIndex = -1
                for (i in 0..<items.size()) {
                    if (items.getAt(i).typeVal.equals(value)) {
                        contained = true
                        currentIndex = i
                    }
                }
                if (!contained) {
                    log.debug("adding {} to list of values in carReportingMark", value)
                    ObsReference obs = new ObsReference()
                    obs.typeVal = value
                    carReportingMark.getItems().add(obs)
                } else {
                    log.debug("not adding item {} as it is already in the list", value)
                    carReportingMark.getSelectionModel().select(currentIndex)
                }
            }

        })
        vendor.setConverter(new StringConverter<ObsReference>() {
            @Override
            String toString(ObsReference object) {
                return object
            }

            @Override
            ObsReference fromString(String string) {
                return new ObsReference(string)
            }
        })
        vendor.setCellFactory(ViewHelpers.cellHelper(vendor))
        vendor.setOnAction({ event ->
            if (vendor.getValue() != null && !vendor.getValue().typeVal.trim().isEmpty()) {
                String value = vendor.getValue().typeVal.trim()
                ObservableList<ObsReference> items = vendor.getItems()
                boolean contained = false
                int currentIndex = -1
                for (i in 0..<items.size()) {
                    if (items.getAt(i).typeVal.toUpperCase().equals(value.toUpperCase())) {
                        contained = true
                        currentIndex = i
                    }
                }
                if (!contained) {
                    log.debug("adding {} to list of values in vendor", value)
                    ObsReference obs = new ObsReference()
                    obs.typeVal = value
                    vendor.getItems().add(obs)
                } else {
                    log.debug("not adding item {} as it is already in the list", value)
                    vendor.getSelectionModel().select(currentIndex)
                }
            }

        })
        manufacturer.setConverter(new StringConverter<ObsReference>() {
            @Override
            String toString(ObsReference object) {
                return object
            }

            @Override
            ObsReference fromString(String string) {
                return new ObsReference(string)
            }
        })
        manufacturer.setCellFactory(ViewHelpers.cellHelper(manufacturer))
        manufacturer.setOnAction({ event ->
            if (manufacturer.getValue() != null && !manufacturer.getValue().typeVal.trim().isEmpty()) {
                String value = manufacturer.getValue().typeVal.trim()
                ObservableList<ObsReference> items = manufacturer.getItems()
                boolean contained = false
                int currentIndex = -1
                for (i in 0..<items.size()) {
                    if (items.getAt(i).typeVal.toUpperCase().equals(value.toUpperCase())) {
                        contained = true
                        currentIndex = i
                    }
                }
                if (!contained) {
                    log.debug("adding {} to list of values in manufacturer", value)
                    ObsReference obs = new ObsReference()
                    obs.typeVal = value
                    manufacturer.getItems().add(obs)
                } else {
                    log.debug("not adding item {} as it is already in the list", value)
                    manufacturer.getSelectionModel().select(currentIndex)
                }
            }

        })
        PrefixSelectionCustomizer.customize(carType)
        PrefixSelectionCustomizer.customize(carAARType)
        PrefixSelectionCustomizer.customize(carPRRType)
        PrefixSelectionCustomizer.customize(carCouplerType)
        PrefixSelectionCustomizer.customize(carKitType)
        PrefixSelectionCustomizer.customize(carLengthUnits)
        PrefixSelectionCustomizer.customize(carWeightUnits)
        messageText.textProperty().bind(model.messageText)
        getApplication().getWindowManager().attach("carEditWindow", stage)
    }


    // build the UI
    private Scene init() {
        Scene scene = new Scene(new Group());
        scene.setFill(Color.WHITE);
        //scene.getStylesheets().add("bootstrapfx.css");

        AnchorPane pane = loadFromFXML();
        //  model.inputProperty().bindBidirectional(input.textProperty());
        //  model.outputProperty().bindBidirectional(output.textProperty());
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        connectActions(pane, controller);

        return scene;
    }


}