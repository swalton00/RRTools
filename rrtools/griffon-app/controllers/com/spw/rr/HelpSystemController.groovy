package com.spw.rr

import griffon.core.artifact.GriffonController
import griffon.core.controller.ControllerAction
import griffon.inject.MVCMember
import griffon.metadata.ArtifactProviderFor
import griffon.transform.Threading
import javafx.stage.Stage

import javax.annotation.Nonnull

@ArtifactProviderFor(GriffonController)
class HelpSystemController {
    @MVCMember @Nonnull
    HelpSystemModel model

    public static String readString(InputStream inputStream) throws IOException {

        ByteArrayOutputStream into = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        for (int n; 0 < (n = inputStream.read(buf));) {
            into.write(buf, 0, n);
        }
        into.close();
        return new String(into.toByteArray(), "UTF-8"); // Or whatever encoding
    }

    void onWindowShown(String windowName, Stage w) {
        if (windowName.equals("helpWindow")) {
            log.debug("setting up the help window")
            InputStream htmlStream = application.resourceHandler.getResourceAsStream('index.html')
            String htmlText = readString(htmlStream)
            model.engine.loadContent(htmlText)
        }
    }
}