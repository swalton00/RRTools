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
import java.awt.*;
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
    DbInfoService dbInfo;

    Bindings binds = Bindings.getInstance();

    private JComboBox<String> commPort;
    private JComboBox<String> unitSystem;
    private JTextField scaleRatio = new JTextField("", 4);
    private JComboBox<String> scale;
    private JComboBox<String> inspectionEvery;
    private JComboBox<String> inspectionUnits;
    private JTextField dbUser = new JTextField("", 8);
    private JPasswordField dbPassword = new JPasswordField("", 8);
    private JTextField dbName = new JTextField("", 8);
    private JTextField locationValue = new JTextField("",60);
    private JTextField dbURL = new JTextField("", 80);
    private JTextField messageField = new JTextField("", 90);
    private JRadioButton radioUseDBName;
    private JRadioButton radioUseURL;
    private JButton okayButton;
    private JButton cancelButton;
    private JButton locationButton;
    private JButton helpButton;
    private JTextField statusMessage;
    protected JDialog window;

    @Override
    public void initUI() {
        model.init();
        Window parent = (Window) application.getWindowManager().findWindow("mainWindow");
        window = new JDialog(parent, "RrTools Preferences", Dialog.ModalityType.APPLICATION_MODAL);
        window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
        commPort = new JComboBox<String>(tempArray);
        panel.add(commPort, "wrap");
        panel.add(new JLabel("Unit System"), "align right");
        unitSystem = new JComboBox<String>(model.unitSystems);
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
        panel.add(inspectionEvery, "split 2");
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
        panel.add(new JLabel("Location"), "align right");
        Action locationAction = toolkitActionFor(controller, "location");
        locationButton = new JButton(locationAction);
        panel.add(locationButton, "split 2");
        locationValue.setText(model.dbLocation);
        panel.add(locationValue, "wrap");
        panel.add(radioUseURL, "wrap");
        panel.add(new JLabel("Database URL"), "align right");
        dbURL.setText(model.dbURL);
        panel.add(dbURL, "wrap");
        Action action = toolkitActionFor(controller, "cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("fillx"));
        buttonPanel.setPreferredSize(new Dimension(450, 15));
        cancelButton = new JButton(action);
        cancelButton.setName("Cancel entries");
        buttonPanel.add(cancelButton);
        Action okayAction = toolkitActionFor(controller, "okay");
        okayButton = new JButton(okayAction);
        okayButton.setName("Confirm Choices");
        buttonPanel.add(okayButton);
        Action helpAction = toolkitActionFor(controller, "help");
        helpButton = new JButton(helpAction);
        buttonPanel.add(helpButton);
        panel.add(buttonPanel, "span 2");
        statusMessage = new JTextField("", 100);
        panel.add(statusMessage, "span");
        // elements have been added, set the drop-downs to the defaults or prior values
        commPort.setSelectedItem(model.selectedComPort);
        unitSystem.setSelectedItem(model.selectedUnitSystem);
        scale.setSelectedItem(model.scaleName);
        inspectionUnits.setSelectedItem(model.inspectionSelectedUnits);
        inspectionEvery.setSelectedItem(model.inspectionEvery);
        if (dbInfo.urlRequired()) {
            radioUseURL.setSelected(true);
        } else {
            radioUseDBName.setSelected(true);

        }
        // now add listeners
        binds.addBinding(scaleRatio, "text", model.scaleRatio);
        binds.addBinding(dbUser, "text", model.dbUsername, model.modedDbUsername);
        binds.addBinding(dbPassword, "text", model.dbPassword);
        binds.addBinding(dbName, "text", model.dbName, model.modedDbName);
        binds.addBinding(dbURL, "text", model.dbURL, model.modedDbURL);
        binds.addBinding(locationValue, "text", model.dbLocation, model.modedDbLocation);
        // and selection listeners for combo boxes
        commPort.addActionListener(this);
        unitSystem.addActionListener(this);
        scale.addActionListener(this);
        radioUseDBName.addActionListener(this);
        radioUseURL.addActionListener(this);
    }

    public void setErrorMessage(String text) {
        log.debug("setting error message to {}", text);
        statusMessage.setText(text);
        statusMessage.setForeground(Color.RED);
    }

    public void setMessage(String text) {
        log.debug("setting status message to {} with color Black", text);
        statusMessage.setText(text);
        statusMessage.setForeground(Color.BLACK);
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
        } else if (src.equals(radioUseDBName)) {
            if (radioUseDBName.isSelected()) {
                model.dbDefType = model.dbDefType.NAME;
            } else {
                model.dbDefType = model.dbDefType.URL;
            }
        } else if (src.equals(radioUseURL)) {
            if (radioUseURL.isSelected()) {
                model.dbDefType = model.dbDefType.URL;
            } else {
                model.dbDefType = model.dbDefType.URL;
            }
        } else {
            log.error("unrecognized source for Combo Box listener source");
        }
    }
}
