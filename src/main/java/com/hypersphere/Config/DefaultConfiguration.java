package com.hypersphere.Config;

import com.hypersphere.GUI.GUIPanel;

import javax.swing.*;

public class DefaultConfiguration extends Configuration{
    private static final String PROJECT_PATH = "PROJECT_PATH";

    public DefaultConfiguration(ConfigurationManager m) {
        super(m);
    }

    public void setProjectPath(String projectPath){
        put(PROJECT_PATH, projectPath);
    }

    public String getProjectPath(){
        return (String) get(PROJECT_PATH, manager.getConfigFile().getParentFile().getParentFile());
    }

    @Override
    public void init(JFrame frame, GUIPanel panel) {
        setProjectPath(getProjectPath());
    }
}
