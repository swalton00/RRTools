package com.spw.rr;

//import griffon.core.controller.Action;
import javax.swing.Action;
import griffon.core.artifact.GriffonView;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.swing.artifact.AbstractSwingGriffonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.miginfocom.swing.*;
import javax.inject.Inject;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@ArtifactProviderFor(GriffonView.class)
public class PreferencesView extends AbstractSwingGriffonView implements ActionListener {

    private static final Logger log = LoggerFactory.getLogger(PreferencesView.class);

    @Inject
    PreferencesModel model;

    @Inject
    PreferencesController controller;

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
    private JRadioButton radioUseDBName;
    private JRadioButton radioUseURL;
    private JButton okayButton;
    private JButton cancelButton;

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
        panel.add(new JLabel("Database password"), "align right");
        dbPassword.setText(model.dbPassword);
        panel.add(dbPassword, "wrap");
        radioUseDBName = new JRadioButton("Use Database Name");
        radioUseURL = new JRadioButton("Use Database URL");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioUseURL);
        buttonGroup.add(radioUseDBName);
        panel.add(radioUseDBName, "wrap");
        panel.add(new JLabel("Database name"), "align right");
        dbName.setText(model.dbName);
        panel.add(dbName, "wrap");
        panel.add(radioUseURL, "wrap");
        panel.add(new JLabel("Database URL"));
        dbURL.setText(model.dbURL);
        panel.add(dbURL, "wrap");
        Action action = toolkitActionFor(controller, "cancel");
        cancelButton = new JButton(action);
        cancelButton.setName("Cancel entries");
        panel.add(cancelButton);
        Action okayAction = toolkitActionFor(controller, "okay");
        okayButton = new JButton(okayAction);
        okayButton.setName("Confirm Choices");
        panel.add(okayButton);
        // elements have been added, set the drop-downs to the defaults or prior values
        commPort.setSelectedItem(model.selectedComPort);
        unitSystem.setSelectedItem(model.selectedUnitSystem);
        scale.setSelectedItem(model.scaleName);
        // now add listeners
        binds.addBinding(scaleRatio, "text", model.scaleRatio);
        binds.addBinding(dbUser, "text", model.dbUsername);
        binds.addBinding(dbPassword, "text", model.dbPassword);
        binds.addBinding(dbName, "text", model.dbName);
        binds.addBinding(dbURL, "text", model.dbURL);
        // and selection listeners for combo boxes
        commPort.addActionListener(this);
        unitSystem.addActionListener(this);
        scale.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> src = (JComboBox) e.getSource();
        if (src == commPort) {
            model.selectedComPort = (String) src.getSelectedItem();
        } else if (src == unitSystem) {
            model.selectedUnitSystem = (String) src.getSelectedItem();
        } else if (src == scale) {
            model.scaleName = (String) src.getSelectedItem();
        } else {
            log.error("unrecognized source for Combo Box listener source");
        }
    }
}
