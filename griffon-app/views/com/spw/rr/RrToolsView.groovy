package com.spw.rr

import com.spw.rr.model.ViewCar
import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.MenuItem
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.stage.Stage
import javafx.scene.paint.Color
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView
import javax.annotation.Nonnull
import javafx.scene.control.ScrollPane
import javafx.scene.control.Label

@ArtifactProviderFor(GriffonView)
class RrToolsView extends AbstractJavaFXGriffonView {

    @MVCMember
    @Nonnull
    RrToolsController controller
    @MVCMember
    @Nonnull
    RrToolsModel model

    @FXML
    Label statusLine

    @FXML
    TableColumn rptMark
    @FXML
    TableColumn carNumber
    @FXML
    TableColumn carType
    @FXML
    TableColumn aarType
    @FXML
    TableView<ViewCar> carList
    @FXML MenuItem editCarActionTarget
    @FXML MenuItem maintainCarActionTarget
    @FXML MenuItem badOrderCarActionTarget
    @FXML MenuItem inspectCarActionTarget
    @FXML MenuItem prefsActionTarget

    public Stage stage

    @Override
    public void initUI() {
        stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle(getApplication().getConfiguration().getAsString("application.title"));
        //stage.setWidth(400);
        //stage.setHeight(600);
        stage.setScene(init());
        getApplication().getWindowManager().attach("mainWindow", stage);
        editCarActionTarget.disableProperty().bind(carList.getSelectionModel().selectedItemProperty().isNull())
        maintainCarActionTarget.disableProperty().bind(carList.getSelectionModel().selectedItemProperty().isNull())
        inspectCarActionTarget.disableProperty().bind(carList.getSelectionModel().selectedItemProperty().isNull())
        badOrderCarActionTarget.disableProperty().bind(carList.getSelectionModel().selectedItemProperty().isNull())
        prefsActionTarget.setText("Preferences")
    }

    // build the UI
    private Scene init() {
        Scene scene = new Scene(new Group());
        scene.setFill(Color.WHITE);
        scene.getStylesheets().add("bootstrapfx.css");

        ScrollPane pane = loadFromFXML();
        model.statusLineProperty().bindBidirectional(statusLine.textProperty())
        //  model.inputProperty().bindBidirectional(input.textProperty());
        //  model.outputProperty().bindBidirectional(output.textProperty());
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        connectActions(pane, controller);

        return scene;
    }

}