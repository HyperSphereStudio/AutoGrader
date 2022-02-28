package com.hypersphere.Grader;

import com.hypersphere.GUI.GUIFrame;

public class GradingFrame extends GUIFrame {

    public GradingFrame(String projectPath) {
        super("Auto Grader GUI", new GradingPanel(projectPath));
    }


}