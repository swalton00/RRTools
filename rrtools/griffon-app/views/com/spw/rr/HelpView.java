package com.spw.rr;

import griffon.core.artifact.GriffonView;
import griffon.core.controller.Action;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.swing.artifact.AbstractSwingGriffonView;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.text.html.*;
import javax.swing.text.Document;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Paths;
import java.net.URL;
import java.net.URI;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@ArtifactProviderFor(GriffonView.class)
public class HelpView extends AbstractSwingGriffonView {
    @MVCMember @Nonnull
    private HelpModel model;
    @MVCMember @Nonnull
    private HelpController controller;
    private Logger log = LoggerFactory.getLogger(HelpView.class);

    private static final String HELP_RESOURCE = "html5/index.html";

    @Override
    public void initUI() {
        JFrame window = new JFrame();
        application.getWindowManager().attach("helpWindow", window);
        window.setTitle("RrTools Help");
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu windowMenu = new JMenu("Window");
        Map<String, Action> actionMap = getApplication().getActionManager().actionsFor(controller);
        windowMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("close").getToolkitAction()));
        menuBar.add(windowMenu);
        window.add(menuBar);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
       /* JEditorPane pane = new JEditorPane();

       *//* String htmlText = "";
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("index.html");
        if (inputStream == null) {
            log.error("Index.html not found");
        }
        try {
            InputStreamReader streamReader = new InputStreamReader(inputStream, java.nio.charset.StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder bldr = new StringBuilder("<html>");
            String line;
             while ((line = reader.readLine()) != null) {
                 bldr.append(line);
             }
             pane.setContentType("text/html");
            pane.setPage(bldr.toString());
       *//*
        HTMLEditorKit editorKit = new HTMLEditorKit();
        HTMLDocument document = new HTMLDocument();*/
        try {
            //document.setBase(new URL("http://html5/index.html"));

            Desktop desktop = Desktop.getDesktop();
            URL resource = application.getResourceHandler().getResourceAsURL(HELP_RESOURCE);
            File resourceFile = Paths.get(resource.toURI()).toFile();
            String filepath = Paths.get(resource.toURI()).toFile().getAbsolutePath();
            String fullUrl = "file://localhost/" + filepath;
            String validUrl = fullUrl.replace("\\", "/");
            log.debug("using a URL of {}", validUrl);
            desktop.browse(URI.create(validUrl));
        } catch (Exception e) {
            log.error("Exception with new URL", e);
        }
      /*  pane.setEditable(false);
        pane.setEditorKit(editorKit);

        try {
            InputStream stream = getResourceAsStream("html5/index.html");
        //pane.setPage(url);
        editorKit.read(stream, document, 0);
    } catch (Exception e)
        {
            log.error("Exception displaying page", e);
            pane.setContentType("text/html");
            pane.setText("<html>404 Page not found.</html>");
        }
        JScrollPane scrollPane = new JScrollPane(pane);
        scrollPane.setPreferredSize(new Dimension(500, 600));
        panel.add(scrollPane);
        window.add(panel);
        window.setSize(new Dimension(600, 500));
        window.setVisible(true);*/
    }
}
