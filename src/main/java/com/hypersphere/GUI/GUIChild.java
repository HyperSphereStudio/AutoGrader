package com.hypersphere.GUI;

import javax.swing.*;

public interface GUIChild<P extends GUIPanel> {
    void init(JFrame frame, P panel);
    void destroy(JFrame frame, P panel);
}
