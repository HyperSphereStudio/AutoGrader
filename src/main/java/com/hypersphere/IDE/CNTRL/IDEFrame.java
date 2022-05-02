package com.hypersphere.IDE.CNTRL;

import com.hypersphere.Config.ConfigurationManager;
import com.hypersphere.GUI.GUIFrame;

public class IDEFrame extends GUIFrame {

    public IDEFrame() {
        super("Basic C IDE", new IDEPanel(new ConfigurationManager()));
        IDEPanel panel = (IDEPanel) getPanel();
        add(panel);
        panel.add(panel.getCSP());
    }


}
