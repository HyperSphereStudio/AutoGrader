package com.hypersphere.Grader;

import com.hypersphere.GUI.GUIPanel;
import com.hypersphere.Utils;

import javax.swing.*;
import java.awt.*;

public class GraderPanel extends GUIPanel<GraderPanel> {

    private final JButton importButton, newProjectButton, openProjectButton;
    private JFrame frame;

    public GraderPanel() {
        importButton = new JButton("Import");
        newProjectButton = new JButton("New Project");
        openProjectButton = new JButton("Open");

        add(importButton);
        add(newProjectButton);
        add(openProjectButton);

        openProjectButton.addActionListener(e -> OpenProject());
    }

    @Override
    public void init(JFrame frame, GraderPanel panel) {
        super.init(frame, panel);
        this.frame = frame;
    }

    public void OpenProject(){
        String dir;
        if((dir = Utils.RequestFile(true, FileDialog.LOAD)) != null){
            new GradingFrame(dir);
            frame.dispose();
        }
    }
}
