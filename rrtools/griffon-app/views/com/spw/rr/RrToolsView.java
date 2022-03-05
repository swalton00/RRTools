package com.spw.rr;

import com.spw.rr.models.TableModel;
import griffon.core.artifact.GriffonView;
import griffon.core.controller.Action;
import griffon.inject.MVCMember;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.swing.artifact.AbstractSwingGriffonView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Nonnull;

import static java.util.Arrays.asList;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import java.awt.BorderLayout;

@ArtifactProviderFor(GriffonView.class)
public class RrToolsView extends AbstractSwingGriffonView {
    private RrToolsModel model;
    private RrToolsController controller;

    @MVCMember
    public void setModel(@Nonnull RrToolsModel model) {
        this.model = model;
    }

    @MVCMember
    public void setController(@Nonnull RrToolsController controller) {
        this.controller = controller;
    }

    @Override
    public void initUI() {
        JFrame window = (JFrame) getApplication()
            .createApplicationContainer(Collections.<String,Object>emptyMap());
        window.setName("mainWindow");
        window.setTitle(getApplication().getConfiguration().getAsString("application.title"));
        window.setSize(320, 500);
        window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        window.setIconImage(getImage("/griffon-icon-48x48.png"));
        window.setIconImages(asList(
            getImage("/griffon-icon-48x48.png"),
            getImage("/griffon-icon-32x32.png"),
            getImage("/griffon-icon-16x16.png")
        ));
        getApplication().getWindowManager().attach("mainWindow", window);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        Map<String, Action> actionMap = getApplication().getActionManager().actionsFor(controller);
        fileMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("export").getToolkitAction()));
        fileMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("import").getToolkitAction()));
        fileMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("backup").getToolkitAction()));
        fileMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("restore").getToolkitAction()));
        fileMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("close").getToolkitAction()));
        JMenu editMenu = new JMenu("Edit");
        Action editAction = actionMap.get("edit");
        Action deleteAction = actionMap.get("delete");
        Action inspectAction = actionMap.get("inspect");
        Action badOrderAction = actionMap.get("badOrder");
        Action maintainAction = actionMap.get("maintain");
        model.addPropertyChangeListener("selected", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                editAction.setEnabled( (boolean) evt.getNewValue());
                deleteAction.setEnabled( (boolean) evt.getNewValue());
                inspectAction.setEnabled( (boolean) evt.getNewValue());
                badOrderAction.setEnabled( (boolean) evt.getNewValue());
                maintainAction.setEnabled( (boolean) evt.getNewValue());
            }
        });
        editMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("edit").getToolkitAction()));
        editMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("delete").getToolkitAction()));
        editMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("inspect").getToolkitAction()));
        editMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("badOrder").getToolkitAction()));
        editMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("maintain").getToolkitAction()));
        editMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("preferences").getToolkitAction()));
        menuBar.add(editMenu);
        window.getContentPane().setLayout(new GridLayout(2, 1));
        JMenu maintainMenu = new JMenu("Maintain");
        maintainMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("carType").getToolkitAction()));
        maintainMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("aarType").getToolkitAction()));
        maintainMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("kitType").getToolkitAction()));
        maintainMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("prrType").getToolkitAction()));
        maintainMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("exportTableData").getToolkitAction()));
        maintainMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("importTableData").getToolkitAction()));
        menuBar.add(maintainMenu);
        JMenu viewMenu = new JMenu("View");
        viewMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("viewAll").getToolkitAction()));
        viewMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("viewMaintenance").getToolkitAction()));
        viewMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("viewInspectNeeded").getToolkitAction()));
        viewMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("viewMissingTags").getToolkitAction()));
        viewMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("viewByRoadName").getToolkitAction()));
        menuBar.add(viewMenu);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem((javax.swing.Action) actionMap.get("help").getToolkitAction()));
        menuBar.setSize(320, 15);
        menuBar.add(helpMenu);
        window.getContentPane().add(menuBar);
        TableModel tableModel = new TableModel(model);
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(320, 480));
        tableScrollPane.setMinimumSize(new Dimension(320, 480));
        window.getContentPane().add(tableScrollPane, BorderLayout.CENTER);

    }

    private Image getImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(RrToolsView.class.getResource(path));
    }
}