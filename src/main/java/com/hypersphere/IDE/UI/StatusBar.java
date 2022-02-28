package com.hypersphere.IDE.UI;

import com.hypersphere.IDE.CNTRL.IDEPanel;
import com.hypersphere.GUI.GUIChild;
import org.fife.rsta.ui.SizeGripIcon;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel implements GUIChild<IDEPanel> {

    private final JLabel label;

    public StatusBar() {
        label = new JLabel("Ready");
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        setLayout(new BorderLayout());
        add(label, BorderLayout.LINE_START);
        add(new JLabel(new SizeGripIcon()), BorderLayout.LINE_END);
        frame.getContentPane().add(this, BorderLayout.SOUTH);
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide) {

    }
}