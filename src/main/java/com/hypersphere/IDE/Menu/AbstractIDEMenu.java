package com.hypersphere.IDE.Menu;

import com.hypersphere.IDE.CNTRL.IDEPanel;
import com.hypersphere.GUI.GUIChild;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractIDEMenu implements GUIChild<IDEPanel> {
    protected final JMenu menu;

    public AbstractIDEMenu(String name){
        this.menu = new JMenu(name);
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide){ }

    @Override
    public void init(JFrame frame, IDEPanel ide){
        ide.getUi().getMenu().getMenuBar().add(menu);
    }

    public JMenu getMenu() {
        return menu;
    }

    protected void addItem(Action a){
        menu.add(new JMenuItem(a));
    }

    protected void add(Component c){menu.add(c);}
}
