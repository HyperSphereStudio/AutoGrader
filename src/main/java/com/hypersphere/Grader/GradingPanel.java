package com.hypersphere.Grader;

import com.hypersphere.Config.ConfigurationManager;
import com.hypersphere.GUI.GUIPanel;
import com.hypersphere.Grader.rubrics.RubricPanel;
import com.hypersphere.IDE.CNTRL.IDEPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GradingPanel extends GUIPanel<GradingPanel> {
    private final IDEPanel idePanel;
    private final Grader grader;
    private final GraderConfiguration config;

    public GradingPanel(ConfigurationManager configurationManager){
        this.config = new GraderConfiguration(configurationManager);



        grader = new Grader(new ArrayList<>(), new ArrayList<>(), 0);
        idePanel = new IDEPanel(configurationManager);
    }

    @Override
    public void init(JFrame frame, GradingPanel panel) {
        super.init(frame, panel);

        setLayout(new GridLayout(1, 2));

        getRubricPanel().init(frame, this);
        config.init(frame, null);
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
        config.destroy(frame, null);
        idePanel.destroy(frame, idePanel);
    }

    public GraderConfiguration getConfiguration() {
        return config;
    }

    public RubricPanel getRubricPanel() {
        return grader.getRubricPanel();
    }
}
