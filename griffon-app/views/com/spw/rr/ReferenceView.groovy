package com.spw.rr

import com.spw.rr.model.ObservableRefItem
import com.spw.rr.model.ReferenceItem
import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonView)
class ReferenceView extends AbstractJavaFXGriffonView {
    @MVCMember @Nonnull
    FactoryBuilderSupport builder
    @MVCMember @Nonnull
    ReferenceController controller
    @MVCMember @Nonnull
    ReferenceModel model

    @FXML TableColumn<SimpleStringProperty, ObservableRefItem> typeColumn
    @FXML TableColumn<SimpleStringProperty, ObservableRefItem> idColumn
    @FXML TableColumn<SimpleStringProperty, ObservableRefItem> descriptionColumn
    @FXML TableView<ObservableRefItem> refTableView


    void initUI() {
        Stage stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle(model.windowTitle);
        stage.initModality(Modality.APPLICATION_MODAL)
        //stage.setWidth(400);
        //stage.setHeight(600);
        stage.setScene(init());
        getApplication().getWindowManager().attach("referenceWindow", stage);
        log.debug("type column is {}", typeColumn)
        log.debug("id column is {}", idColumn)
        log.debug("description column is {}", descriptionColumn)
        log.debug("Table view is {}", refTableView)
        idColumn.setCellValueFactory( {cellData -> cellData.getValue().id })
        typeColumn.setCellValueFactory({ cellData -> cellData.getValue().typeName})
        descriptionColumn.setCellValueFactory( {cellData -> cellData.getValue()."typeDescription"})
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