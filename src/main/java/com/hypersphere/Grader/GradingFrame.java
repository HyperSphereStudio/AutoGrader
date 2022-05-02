package com.hypersphere.Grader;

import com.hypersphere.Config.ConfigurationManager;
import com.hypersphere.GUI.GUIFrame;

public class GradingFrame extends GUIFrame {

    public GradingFrame(ConfigurationManager manager) {
        super("Auto Grader GUI", new GradingPanel(manager));
    }


}