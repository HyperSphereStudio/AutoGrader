package com.hypersphere.IDE.UI;

import com.hypersphere.GUI.GUIChild;
import com.hypersphere.GUI.GUIFrame;
import com.hypersphere.IDE.CNTRL.IDEPanel;
import com.hypersphere.IDE.Menu.IDEMenu;
import org.fife.rsta.ui.CollapsibleSectionPanel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.*;

public class IDEUI implements GUIChild<IDEPanel> {

    private final StatusBar statusBar;
    private final IDEMenu menu;
    private final IDETextArea ta;
    private final CollapsibleSectionPanel csp;

    public IDEUI(){
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
        return ta;
    }

    public IDETextArea getTextArea() {
        return ta;
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        menu.init(frame, ide);
        ta.init(frame, ide);
        statusBar.init(frame, ide);
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide) {
        menu.destroy(frame, ide);
        ta.destroy(frame, ide);
        statusBar.destroy(frame, ide);
    }

}

