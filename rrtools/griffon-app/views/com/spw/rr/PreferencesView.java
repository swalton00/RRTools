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

    Bindings binds = Bindings.getInstance();

    private JComboBox<String> commPort;
    private JComboBox<String> unitSystem;
    private JTextField scaleRatio = new JTextField("");
    private JComboBox<String> scale;
    private JComboBox<String> inspectionEvery;
    private JComboBox<String> inspectionUnits;
    private JTextField dbUser = new JTextField("");
    private JPasswordField dbPassword = new JPasswordField("xxxx");
    private JTextField dbName = new JTextField("");
    private JTextField locationValue = new JTextField("");
    private JTextField dbURL = new JTextField("");
    private JTextField messageField = new JTextField("");

    @Override
    public void initUI() {
        model.init();
        JFrame window = new JFrame();
        application.getWindowManager().attach("prefs", window);
        window.setTitle("RrTools Preferences");
        window.setSize(new Dimension(450, 800));
        JPanel panel = new JPanel();
        window.add(panel);
        panel.setLayout(new MigLayout("fill"));
        panel.add(new JLabel("Comm Port:"), "align right");
        String[] tempArray = new String[model.commPorts.size()];
        for (int i = 0; i < model.commPorts.size(); i++) {
            tempArray[i] = model.commPorts.get(i);
        }
        commPort = new JComboBox<String>( tempArray);
        panel.add(commPort, "wrap");
        panel.add(new JLabel("Unit System"), "align right");
        unitSystem = new JComboBox<String>( model.unitSystems);
        panel.add(unitSystem, "wrap");
        panel.add(new JLabel("Scale Ratio"), "align right");
        scaleRatio.setText(model.scaleRatio);
        panel.add(scaleRatio, "wrap");
        binds.addBinding(scaleRatio, "text", model.scaleRatio);
        panel.add(new JLabel("Scale"), "align right");
        scale = new JComboBox<String>(model.scales);
        panel.add(scale, "wrap");
        panel.add(new JLabel("Inspection Every"), "align right");
        inspectionEvery = new JComboBox<String>(model.inspectionFrequency);
        inspectionUnits = new JComboBox<String>(model.inspectionUnits);
        panel.add(inspectionEvery, "cell 1 4");
        panel.add(inspectionUnits, "wrap");
        panel.add(new JLabel("Database Username"), "align right");
        dbUser.setText(model.dbUsername);
        panel.add(dbUser, "wrap");
        binds.addBinding(dbUser, "text", model.dbUsername);
        panel.add(new JLabel("Database password"), "align right");
        dbPassword.setText(model.dbPassword);
        panel.add(dbPassword, "wrap");
        binds.addBinding(dbPassword, "text", model.dbPassword);
        panel.add(new JLabel("Database name"), "align right");
        dbName.setText(model.dbName);
        panel.add(dbName, "wrap");
        binds.addBinding(dbName, "text", model.dbName);
        // elements have been added, set the drop-downs to the defaults or prior values



    }
}
