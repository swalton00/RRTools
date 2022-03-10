package com.spw.rr;

import griffon.core.artifact.GriffonView;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.miginfocom.swing.*;
import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

@ArtifactProviderFor(GriffonView.class)
public class PreferencesView extends AbstractGriffonView {

    private static final Logger log = LoggerFactory.getLogger(PreferencesView.class);

    @Inject
    PreferencesModel model;

    @Inject
    SerialDataService dataService;

    @Inject
    PropertyService propertyService;

    private JComboBox<String> commPort = new JComboBox<>();
    private JComboBox<String> unitSystem = new JComboBox<String>();
    private JTextField scaleRatio = new JTextField("");
    private JComboBox<String> scale = new JComboBox<>();
    private JComboBox<String> inspectionEvery = new JComboBox<>();
    private JComboBox<String> inspectionUnits = new JComboBox<>();
    private JTextField dbUser = new JTextField("");
    private JPasswordField dbPassword = new JPasswordField("xxxx");
    private JTextField dbName = new JTextField("");
    private JTextField locationValue = new JTextField("");
    private JTextField dbURL = new JTextField("");
    private JTextField messageField = new JTextField("");

    @Override
    public void initUI() {
        JFrame window = new JFrame();
        application.getWindowManager().attach("prefs", window);
        window.setTitle("RrTools Preferences");
        window.setSize(new Dimension(450, 800));
        JPanel panel = new JPanel();
        window.add(panel);
        panel.setLayout(new MigLayout("fill"));
        panel.add(new JLabel("Comm Port:"), "align right");
        panel.add(commPort, "wrap");
        panel.add(new JLabel("Unit System"), "align right");
        panel.add(unitSystem, "wrap");
        panel.add(new JLabel("Scale Ratio"), "align right");
        panel.add(scaleRatio, "wrap");
        panel.add(new JLabel("Scale"), "align right");
        panel.add(scale, "wrap");
        panel.add(new JLabel("Inspection Every"), "align right");
        panel.add(inspectionEvery, "cell 1 4");
        panel.add(inspectionUnits, "wrap");
        panel.add(new JLabel("Database Username"), "align right");
        panel.add(dbUser, "wrap");
        panel.add(new JLabel("Database password"), "align right");
        panel.add(dbPassword, "wrap");
        panel.add(new JLabel("Database name"), "align right");
        panel.add(dbName, "wrap");

    }
}
