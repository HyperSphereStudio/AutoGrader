package com.hypersphere.GUI;

import javax.swing.*;

public class GUIPanel<T extends GUIPanel> extends JPanel implements GUIChild<T>{

    @Override
    public void init(JFrame frame, T panel) {
        setVisible(true);
    }

    @Override
    public void destroy(JFrame frame, T panel) {

    }

}
