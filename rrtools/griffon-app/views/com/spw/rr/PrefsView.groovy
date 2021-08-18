package com.spw.rr

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.control.PasswordField
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.control.Label
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

    @FXML ChoiceBox comPortList

    @FXML ChoiceBox unitSystemList

    @FXML TextField scaleRatio

    @FXML ChoiceBox scaleName
    @FXML ChoiceBox inspectionFrequency
    @FXML ChoiceBox inspectionUnits
    @FXML TextField dbUsername
    @FXML PasswordField dbPassword
    @FXML TextField dbName
    @FXML TextField dbLocation
    @FXML TextField databaseURL
    @FXML RadioButton useDbLoc
    @FXML RadioButton useUrl
    @FXML Label prefsMessage

    Stage prefsStage

    void initUI() {
        Stage stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        prefsStage = stage
        stage.setTitle("Set RRTools Preferences");
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.setScene(init());
        unitSystemList.getItems().addAll("English", "Metric")
        scaleName.getItems().addAll("Z", "N", "TT", "HO", "OO", "S", "O", "F", "G", "1/4 In", "1 In", "Other")
        inspectionFrequency.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
        inspectionUnits.getItems().addAll("Months", "Years")
        model.scaleRatio.bindBidirectional(scaleRatio.textProperty())
        model.inspectionUnits.bindBidirectional(inspectionUnits.valueProperty())
        model.inspectionEvery.bindBidirectional(inspectionFrequency.valueProperty())
        model.dbUsername.bindBidirectional(dbUsername.textProperty())
        model.dbPassword.bindBidirectional(dbPassword.textProperty())
        model.dbLocation.bindBidirectional(dbLocation.textProperty())
        model.dbURL.bindBidirectional(databaseURL.textProperty())
        model.dbName.bindBidirectional(dbName.textProperty())
        getApplication().getWindowManager().attach("prefsWindow", stage);
        model.useDbUrl.bindBidirectional(useUrl.selectedProperty())
        model.useDbLocation.bindBidirectional(useDbLoc.selectedProperty())
        model.message.bindBidirectional(prefsMessage.textProperty())
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