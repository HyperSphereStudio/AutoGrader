package com.hypersphere.IDE.UI;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.IDEObj;
import com.hypersphere.IDE.Menu.IDEMenu;
import org.fife.rsta.ui.CollapsibleSectionPanel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.*;

public class IDEUI implements IDEObj {

    private final JPanel cp;
    private final StatusBar statusBar;
    private final IDEMenu menu;
    private final IDETextArea ta;
    private final CollapsibleSectionPanel csp;

    public IDEUI(){
        cp = new JPanel(new BorderLayout());
        csp = new CollapsibleSectionPanel();
        menu = new IDEMenu();
        ta = new IDETextArea();
        statusBar = new StatusBar();
    }



    public StatusBar getStatusBar() {
        return statusBar;
    }

    public IDEMenu getMenu() {
        return menu;
    }

    public CollapsibleSectionPanel getCsp() {
        return csp;
    }

    public RSyntaxTextArea getRTextArea() {
        return ta.getTextArea();
    }

    public IDETextArea getTextArea() {
        return ta;
    }

    public JPanel getContentPane() {
        return cp;
    }

    @Override
    public void init(IDE ide) {
        ide.setContentPane(cp);
        cp.add(csp);
        menu.init(ide);
        ta.init(ide);
        statusBar.init(ide);
    }

    @Override
    public void destroy(IDE ide) {
        menu.destroy(ide);
        ta.destroy(ide);
        statusBar.destroy(ide);
    }

}

