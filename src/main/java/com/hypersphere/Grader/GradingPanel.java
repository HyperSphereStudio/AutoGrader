package com.hypersphere.Grader;

import com.hypersphere.Configuration;
import com.hypersphere.GUI.GUIPanel;
import com.hypersphere.IDE.CNTRL.IDEPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GradingPanel extends GUIPanel<GradingPanel> {
    private final IDEPanel idePanel;
    private final Grader grader;
    private final Configuration configuration;
    private final String projectPath;

    public GradingPanel(String projectPath){
        this.projectPath = projectPath;
        grader = new Grader(new ArrayList<>());
        configuration = new Configuration();
        idePanel = new IDEPanel();
        configuration.setProjectPath(projectPath);
    }

    @Override
    public void init(JFrame frame, GradingPanel panel) {
        super.init(frame, panel);

        setLayout(new GridLayout(1, 2));

        getRubricPanel().init(frame, this);
        configuration.init(frame, null);
        idePanel.init(frame, idePanel);

        add(idePanel);
        idePanel.add(idePanel.getCSP());

        add(getRubricPanel());

        frame.setResizable(true);
    }

    @Override
    public void destroy(JFrame frame, GradingPanel panel) {
        super.destroy(frame, panel);
        getRubricPanel().destroy(frame, this);
        configuration.destroy(frame, null);
        idePanel.destroy(frame, idePanel);
    }

    public String getProjectPath(){
        return projectPath;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public RubricPanel getRubricPanel() {
        return grader.getRubricPanel();
    }
}
