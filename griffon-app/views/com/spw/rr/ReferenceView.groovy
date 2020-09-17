package com.spw.rr

import com.spw.rr.model.ObservableRefItem
import com.spw.rr.model.ReferenceItem
import griffon.core.artifact.GriffonView
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Callback
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
    @FXML Button refAddNewItemActionTarget
    @FXML Button refSaveActionTarget
    @FXML TextField refNewType
    @FXML TextField refNewDescription

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
        Callback<TableColumn<ObservableRefItem, String>, TableCell<ObservableRefItem, String>> cellFactory =
                { TableColumn<ObservableRefItem, String> p -> new EditingCell() }
        refTableView.setEditable(true)
        idColumn.setCellValueFactory( new PropertyValueFactory<ObservableRefItem, String>("id"))
        typeColumn.setCellFactory(cellFactory)
        typeColumn.setCellValueFactory(new PropertyValueFactory<ObservableRefItem, String>("typeName"))
        typeColumn.setOnEditCommit({
            it.getTableView().getItems().get(it.getTablePosition().getRow()).setTypeName(it.getNewValue())
        })
        descriptionColumn.setCellFactory(cellFactory)
        descriptionColumn.setCellValueFactory( new PropertyValueFactory<ObservableRefItem, String>("typeDescription"))
        descriptionColumn.setOnEditCommit({
            it.getTableView().getItems().get(it.getTablePosition().getRow()).setTypeDescription(it.getNewValue())
        })
        refAddNewItemActionTarget.disableProperty().bind(model.refEnterNotReady)
        refSaveActionTarget.disableProperty().bind(model.refDataClean)
        refNewType.textProperty().bindBidirectional(model.newTypeValue)
        refNewDescription.textProperty().bindBidirectional(model.newDescriptionValue)
    }

    class EditingCell extends TableCell<ReferenceItem, String> {
        private TextField textField
        public EditingCell() {}
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit()
                createTextField()
                setText(null)
                setGraphic(textField)
                textField.selectAll()

            }
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit()
            setText((String)getItem())
//            setGraphic(null)
        }
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty)
            if (empty) {
                setText(null)
//                setGraphic(null)
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString())
                    }
                    setText(null)
                    setGraphic(textField)
                } else  {
                    setText(getString())
      //              setGraphic(null)
                }
            }
        }
        private void createTextField() {
            textField = new TextField(getString())
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2)
            textField.focusedProperty().addListener( new ChangeListener<Boolean>() {
                @Override
                void changed(ObservableValue<? extends  Boolean> obs, Boolean oldVal, Boolean newVal) {
                    if (!newVal) {
                        commitEdit(textField.getText())
                        model.refDataClean.set(false)
                    }
                }
            } )
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString()
        }
    }


    // build the UI
    private Scene init() {
        Scene scene = new Scene(new Group());
        scene.setFill(Color.WHITE);
        //scene.getStylesheets().add("bootstrapfx.css");

        AnchorPane pane = loadFromFXML();
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        connectActions(pane, controller);

        return scene;
    }
}