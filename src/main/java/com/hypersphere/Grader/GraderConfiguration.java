package com.hypersphere.Grader;

import com.hypersphere.Config.Configuration;
import com.hypersphere.Config.ConfigurationManager;

import javax.swing.*;

public class GraderConfiguration extends Configuration<GraderStartPanel> {

    public GraderConfiguration(ConfigurationManager manager) {
        super(manager);
    }

    @Override
    public void init(JFrame frame, GraderStartPanel grader) {
        super.init(frame, grader);

    }

    @Override
    public void destroy(JFrame frame, GraderStartPanel grader) {
        super.destroy(frame, grader);

    }

}
