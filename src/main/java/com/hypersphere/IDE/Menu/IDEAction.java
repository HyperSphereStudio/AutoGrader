package com.hypersphere.IDE.Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class IDEAction extends AbstractIDEAction{

    private final Action a;

    public IDEAction(Action a, String name) {
        super(name);
        this.a = a;
    }

    public IDEAction(Action a, String name, int key) {
        super(name, key);
        this.a = a;
    }

    public IDEAction(Action a, String name, int key, boolean control, boolean shift) {
        super(name, key, control, shift);
        this.a = a;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        a.actionPerformed(e);
    }
}
