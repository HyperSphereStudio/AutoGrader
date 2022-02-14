package com.hypersphere.IDE.UI;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.IDEObj;
import org.fife.rsta.ui.SizeGripIcon;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel implements IDEObj {

    private final JLabel label;

    public StatusBar() {
        label = new JLabel("Ready");
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    @Override
    public void init(IDE ide) {
        setLayout(new BorderLayout());
        add(label, BorderLayout.LINE_START);
        add(new JLabel(new SizeGripIcon()), BorderLayout.LINE_END);
        ide.getContentPane().add(this, BorderLayout.SOUTH);
    }

    @Override
    public void destroy(IDE ide) {

    }
}