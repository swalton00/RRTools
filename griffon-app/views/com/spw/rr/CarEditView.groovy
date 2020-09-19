package com.spw.rr

import com.spw.rr.model.ObsReference
import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ComboBox
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
import javax.swing.text.html.ListView

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
    TextField carBltDate
    @FXML
    TextArea carDescription


    void initUI() {
        Stage stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle(model.windowTitle);
        stage.initModality(Modality.APPLICATION_MODAL)
        //stage.setWidth(400);
        //stage.setHeight(600);
        stage.setScene(init());
        model.carTag.bindBidirectional(carTag.textProperty())
        carLengthUnits.setItems(FXCollections.observableArrayList("inches", "centimeters"))
        carLengthUnits.getSelectionModel().select(0)
        carWeightUnits.setItems(FXCollections.observableArrayList("ounces", "grams"))
        carWeightUnits.getSelectionModel().select(0)
        model.carDescription.bindBidirectional(carDescription.textProperty())
        model.carWheels.bindBidirectional(carWheels.textProperty())
        model.bltDate.bindBidirectional(carBltDate.textProperty())
        model.carColor.bindBidirectional(carColor.textProperty())
        model.carNumber.bindBidirectional(carNumber.textProperty())
        model.carLength.bindBidirectional(carLength.textProperty())
        model.carWeight.bindBidirectional(carWeight.textProperty())
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