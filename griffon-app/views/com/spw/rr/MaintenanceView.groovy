package com.spw.rr

import com.spw.rr.model.BOViewModel
import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.CheckBox
import javafx.scene.control.ChoiceBox
import javafx.scene.control.DatePicker
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView
import javax.annotation.Nonnull
import java.awt.event.KeyEvent


@ArtifactProviderFor(GriffonView)
class MaintenanceView extends AbstractJavaFXGriffonView {
    @MVCMember @Nonnull
    FactoryBuilderSupport builder
    @MVCMember @Nonnull
    MaintenanceController controller
    @MVCMember @Nonnull
    MaintenanceModel model

    @FXML Label reportingMark
    @FXML Label carNumber
    @FXML DatePicker datePerformed
    @FXML Label currentTime
    @FXML TextField timeRequired
    @FXML ChoiceBox<String> carArea
    @FXML TextArea probDesc
    @FXML TableView badOrderedCars
    @FXML TableColumn<BOViewModel, String> entryDateColumn
    @FXML TableColumn<BOViewModel, String> areaOfCar
    @FXML TableColumn<BOViewModel, String> selectColumn
    @FXML TableColumn<BOViewModel, String> problem
    @FXML TextArea workPerformed
    @FXML CheckBox closeSelected
    @FXML Label messageArea


    void initUI() {
        log.debug("Initializing the Maintenance View")
        Stage stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle("Performing Maintenance on car");
        stage.initModality(Modality.APPLICATION_MODAL)
        //stage.setWidth(400);
        //stage.setHeight(600);
        stage.setScene(init());
        getApplication().getWindowManager().attach("MaintenanceWindow", stage)
        reportingMark.textProperty().bind(model.reportingMark)
        carNumber.textProperty().bind(model.carNumber)
        currentTime.textProperty().bind(model.currentTime)
        timeRequired.textProperty().bindBidirectional(model.timeRequired)
        model.selectedCarArea.bind(carArea.getSelectionModel().selectedIndexProperty())
        probDesc.textProperty().bindBidirectional(model.probDesc)
        workPerformed.textProperty().bindBidirectional(model.workPerformed)
        datePerformed.valueProperty().bindBidirectional(model.datePerformed)
        messageArea.textProperty().bind(model.messages)
        closeSelected.selectedProperty().bindBidirectional(model.closeSelected)
        //closeSelected.disableProperty().bind(model.disableClose)
        badOrderedCars.setItems(model.badOrderItems)
        entryDateColumn.setCellValueFactory(new PropertyValueFactory<BOViewModel, String>("date_entered"))
        areaOfCar.setCellValueFactory(new PropertyValueFactory<BOViewModel, String>("carArea"))
        selectColumn.setCellValueFactory(new PropertyValueFactory<BOViewModel, String>("selected"))
        problem.setCellValueFactory(new PropertyValueFactory<BOViewModel, String>("problemDescription"))
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