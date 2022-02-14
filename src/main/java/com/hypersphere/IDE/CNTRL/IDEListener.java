package com.hypersphere.IDE.CNTRL;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class IDEListener implements WindowListener {

    private IDE ide;

    public IDEListener(IDE ide){
        this.ide = ide;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        ide.destroy();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
