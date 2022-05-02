package com.hypersphere.Grader;

import com.hypersphere.Config.ConfigurationManager;
import com.hypersphere.GUI.GUIPanel;
import com.hypersphere.Utils;

import javax.swing.*;
import java.awt.*;

public class GraderStartPanel extends GUIPanel<GraderStartPanel> {

    private final JButton importButton, newProjectButton, openProjectButton;
    private final ConfigurationManager manager;
    private JFrame frame;

    public GraderStartPanel(ConfigurationManager manager) {
        this.manager = manager;
        importButton = new JButton("Import");
        newProjectButton = new JButton("New Project");
        openProjectButton = new JButton("Open");

        add(importButton);
        add(newProjectButton);
        add(openProjectButton);

        openProjectButton.addActionListener(e -> OpenProject());
    }

    @Override
    public void init(JFrame frame, GraderStartPanel panel) {
        super.init(frame, panel);
        this.frame = frame;
    }

    @Override
    public void destroy(JFrame frame, GraderStartPanel panel) {
        super.destroy(frame, panel);
    }

    public void OpenProject(){
        String dir;
        if((dir = Utils.RequestFile(true, FileDialog.LOAD)) != null){
            manager.getDefaultConfig().setProjectPath(dir);
            new GradingFrame(manager);
            frame.dispose();
        }
    }
}
