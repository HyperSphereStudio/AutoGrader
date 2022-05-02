package com.hypersphere.Grader;

import com.hypersphere.Config.ConfigurationManager;
import com.hypersphere.GUI.GUIFrame;

public class GraderStartFrame extends GUIFrame {
    public GraderStartFrame() {
        super("Auto Grader GUI", new GraderStartPanel(new ConfigurationManager()));
    }
}
