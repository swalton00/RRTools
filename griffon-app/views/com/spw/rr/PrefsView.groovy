package com.spw.rr

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonView)
class PrefsView extends AbstractJavaFXGriffonView {
    @MVCMember
    @Nonnull
    FactoryBuilderSupport builder
    @MVCMember
    @Nonnull
    PrefsController controller
    @MVCMember
    @Nonnull
    PrefsModel model

    @FXML private ChoiceBox comPortList

    @FXML private ChoiceBox unitSystemList

    @FXML private TextField scaleRatio

    @FXML ChoiceBox scaleName
    @FXML ChoiceBox inspectionFrequency
    @FXML ChoiceBox inspectionUnits


    void initUI() {
        Stage stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle("Set RRTools Preferences");
        stage.initModality(Modality.APPLICATION_MODAL)
        //stage.setWidth(400);
        //stage.setHeight(600);
        stage.setScene(init());
        unitSystemList.getItems().addAll("English", "Metric")
        scaleName.getItems().addAll("Z", "N", "TT", "HO", "OO", "S", "O", "F", "G", "1/4 In", "1 In", "Other")
        inspectionFrequency.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
        inspectionUnits.getItems().addAll("Month", "Year")

        getApplication().getWindowManager().attach("prefsWindow", stage);
    }


    // build the UI
    private Scene init() {
        Scene scene = new Scene(new Group());
        scene.setFill(Color.WHITE);
        //scene.getStylesheets().add("bootstrapfx.css");

        GridPane pane = loadFromFXML();
        //  model.inputProperty().bindBidirectional(input.textProperty());
        //  model.outputProperty().bindBidirectional(output.textProperty());
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        connectActions(pane, controller);

        return scene;
    }
}