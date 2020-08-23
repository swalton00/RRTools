package com.spw.rr

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.paint.Color
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView
import javax.annotation.Nonnull
import javafx.scene.control.ScrollPane

@ArtifactProviderFor(GriffonView)
class RrToolsView extends AbstractJavaFXGriffonView {

    @MVCMember @Nonnull
    RrToolsController controller
    @MVCMember @Nonnull
    RrToolsModel model

    @Override
    public void initUI() {
        Stage stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle(getApplication().getConfiguration().getAsString("application.title"));
        stage.setWidth(400);
        stage.setHeight(120);
        stage.setScene(init());
        getApplication().getWindowManager().attach("mainWindow", stage);
    }

    // build the UI
    private Scene init() {
        Scene scene = new Scene(new Group());
        scene.setFill(Color.WHITE);
        scene.getStylesheets().add("bootstrapfx.css");

        ScrollPane pane = loadFromFXML();
      //  model.inputProperty().bindBidirectional(input.textProperty());
      //  model.outputProperty().bindBidirectional(output.textProperty());
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        connectActions(pane, controller);

        return scene;
    }

}