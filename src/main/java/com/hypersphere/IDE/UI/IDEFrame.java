package com.hypersphere.IDE.UI;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.IDEObj;

import javax.swing.*;

public class IDEFrame extends JFrame implements IDEObj {

    public IDEFrame(String title){
        setTitle(title);
    }

    @Override
    public void init(IDE ide){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        requestFocusInWindow();
        SwingUtilities.invokeLater(() -> super.setVisible(true));
    }

    @Override
    public void destroy(IDE ide) {

    }
}
