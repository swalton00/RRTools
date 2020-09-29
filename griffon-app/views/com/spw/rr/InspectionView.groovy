package com.spw.rr

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import javafx.scene.control.DatePicker
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.Tooltip
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonView)
class InspectionView extends AbstractJavaFXGriffonView {
    @MVCMember
    @Nonnull
    FactoryBuilderSupport builder
    @MVCMember
    @Nonnull
    InspectionController controller
    @MVCMember
    @Nonnull
    InspectionModel model

    @FXML
    Label carNumber
    @FXML
    Label reportingMark
    @FXML
    Label startTime
    @FXML
    Label weightCalculated
    @FXML
    public DatePicker inspectionDate
    @FXML
    CheckBox doesWeightPass
    @FXML
    TextField carWeight
    @FXML
    TextField carLength
    @FXML
    TextField timeRequired
    @FXML
    ChoiceBox<String> weightUnits
    @FXML
    ChoiceBox<String> lengthUnits
    @FXML
    ChoiceBox<String> carScale
    @FXML
    CheckBox couplerHeightPassA
    @FXML
    CheckBox couplerHeightPassB
    @FXML
    CheckBox couplerActionPassA
    @FXML
    CheckBox couplerActionPassB
    @FXML
    CheckBox metalWheelsA
    @FXML
    CheckBox metalWheelsB
    @FXML
    CheckBox resistanceWheelsA
    @FXML
    CheckBox resistanceWheelsB
    @FXML
    CheckBox wheelGaugeA
    @FXML
    CheckBox wheelGaugeB
    @FXML
    CheckBox wheelTreadA
    @FXML
    CheckBox wheelTreadB
    @FXML
    CheckBox truckScrewTightB
    @FXML
    CheckBox truckScrewLooseA
    @FXML
    CheckBox carSitsLevel
    @FXML
    CheckBox carDoesNotRock
    @FXML
    CheckBox allWheelsTouch
    @FXML
    CheckBox overallResults
    @FXML
    Label inspectionMessage

    void initUI() {
        Stage stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle("Inspecting Car");
        stage.initModality(Modality.APPLICATION_MODAL)
        //stage.setWidth(400);
        //stage.setHeight(600);
        stage.setScene(init());
        getApplication().getWindowManager().attach("InspectionWindow", stage)
        overallResults.setStyle("-fx-opacity: 1")
        doesWeightPass.setStyle("-fx-opacity: 1")
        model.doesWeightPass.bindBidirectional(doesWeightPass.selectedProperty())
        model.obsCarNumber.bindBidirectional(carNumber.textProperty())
        model.obsReportingMark.bindBidirectional(reportingMark.textProperty())
        model.carLength.bindBidirectional(carLength.textProperty())
        carLength.setTooltip(new Tooltip("Enter the car length to the nearest tenth"))
        model.carWeight.bindBidirectional(carWeight.textProperty())
        carWeight.setTooltip(new Tooltip("Enter the weight of the car to the nearest tenth"))
        model.couplerHeightPassA.bindBidirectional(couplerHeightPassA.selectedProperty())
        couplerActionPassA.setTooltip(new Tooltip("Does the coupler height pass?"))
        model.couplerHeightPassB.bindBidirectional(couplerHeightPassB.selectedProperty())
        model.couplerActionPassA.bindBidirectional(couplerActionPassA.selectedProperty())
        model.couplerActionPassB.bindBidirectional(couplerActionPassB.selectedProperty())
        model.metalWheelsA.bindBidirectional(metalWheelsA.selectedProperty())
        model.metalWheelsB.bindBidirectional(metalWheelsB.selectedProperty())
        model.resistanceWheelsA.bindBidirectional(resistanceWheelsA.selectedProperty())
        model.resistanceWheelsB.bindBidirectional(resistanceWheelsB.selectedProperty())
        model.wheelGaugeA.bindBidirectional(wheelGaugeA.selectedProperty())
        model.wheelGaugeB.bindBidirectional(wheelGaugeB.selectedProperty())
        model.wheelTreadA.bindBidirectional(wheelTreadA.selectedProperty())
        model.wheelTreadB.bindBidirectional(wheelTreadB.selectedProperty())
        model.truckScrewTightB.bindBidirectional(truckScrewTightB.selectedProperty())
        model.truckScrewLooseA.bindBidirectional(truckScrewLooseA.selectedProperty())
        model.carSitsLevel.bindBidirectional(carSitsLevel.selectedProperty())
        model.carDoesNotRock.bindBidirectional(carDoesNotRock.selectedProperty())
        model.allWheelsTouch.bindBidirectional(allWheelsTouch.selectedProperty())
        model.overallResults.bindBidirectional(overallResults.selectedProperty())
        model.startTime.bindBidirectional(startTime.textProperty())
        model.weightCalculated.bindBidirectional(weightCalculated.textProperty())
        //model.inspectionDate.bindBidirectional(inspectionDate.valueProperty())
        model.timeRequired.bindBidirectional(timeRequired.textProperty())
        model.inspectionMessage.bindBidirectional(inspectionMessage.textProperty())
        carScale.setItems(FXCollections.observableArrayList("Z", "N", "HO", "S", "O", "G"))
        carScale.getSelectionModel().select(2)
        lengthUnits.setItems(FXCollections.observableArrayList("Inches", "Centimeters"))
        lengthUnits.getSelectionModel().select(0)
        weightUnits.setItems((FXCollections.observableArrayList("Ounces", "Grams")))
        weightUnits.getSelectionModel().select(0)
        model.overallResults.bind(doesWeightPass.selectedProperty()
                .and(couplerHeightPassB.selectedProperty()
                        .and(couplerHeightPassA.selectedProperty()
                                .and(couplerActionPassB.selectedProperty()
                                        .and(metalWheelsB.selectedProperty()
                                                .and(metalWheelsA.selectedProperty())))
                        )))
    }

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