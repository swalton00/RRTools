package com.spw.rr

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonView)
class CarEditView extends AbstractJavaFXGriffonView {
    @MVCMember @Nonnull
    FactoryBuilderSupport builder
    @MVCMember @Nonnull
    CarEditController controller
    @MVCMember @Nonnull    CarEditModel model

    @FXML Label carId
    @FXML private ChoiceBox carReportingMark
    @FXML TextField carNumber
    @FXML private ChoiceBox carType
    @FXML private ChoiceBox carAARType
    @FXML private ChoiceBox carTruckType
    @FXML private ChoiceBox carCouplerType
    @FXML TextField carLength
    @FXML TextField carWeight



    void initUI() {
        Stage stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle(model.windowTitle);
        stage.initModality(Modality.APPLICATION_MODAL)
        //stage.setWidth(400);
        //stage.setHeight(600);
        stage.setScene(init());
        getApplication().getWindowManager().attach("carEditWindow", stage);
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