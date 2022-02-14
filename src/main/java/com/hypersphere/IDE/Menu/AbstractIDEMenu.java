package com.hypersphere.IDE.Menu;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.IDEObj;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractIDEMenu implements IDEObj {
    protected final JMenu menu;

    public AbstractIDEMenu(String name){
        this.menu = new JMenu(name);
    }

    @Override
    public void destroy(IDE ide){ }

    @Override
    public void init(IDE ide){
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
