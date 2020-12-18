package com.spw.rr

import griffon.core.artifact.GriffonView
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import javafx.fxml.FXML
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import javafx.stage.Stage
import org.codehaus.griffon.runtime.javafx.artifact.AbstractJavaFXGriffonView
import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonView)
class HelpSystemView extends AbstractJavaFXGriffonView {
    @MVCMember
    @Nonnull
    HelpSystemController controller
    @MVCMember
    @Nonnull
    HelpSystemModel model

    @FXML WebView webView

    public Stage stage

    void initUI() {
        stage = (Stage) getApplication()
                .createApplicationContainer(Collections.emptyMap());
        stage.setTitle("RR Tools Help");
        //stage.setWidth(400);
        //stage.setHeight(600);
        stage.setScene(init());
        model.engine = webView.getEngine()
        getApplication().getWindowManager().attach("helpWindow", stage);
    }

// build the UI
    private Scene init() {
        Scene scene = new Scene(new Group());
        scene.setFill(Color.WHITE);
        scene.getStylesheets().add("bootstrapfx.css");

        AnchorPane pane = loadFromFXML();
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        connectActions(pane, controller);
        return scene;
    }

}