package com.hypersphere.IDE.CNTRL;

import com.hypersphere.GUI.GUIFrame;

public class IDEFrame extends GUIFrame {

    public IDEFrame() {
        super("Basic C IDE", new IDEPanel());
        IDEPanel panel = (IDEPanel) getPanel();
        add(panel);
        panel.add(panel.getCSP());
    }


}
