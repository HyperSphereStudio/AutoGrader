package com.hypersphere.IDE.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

public abstract class AbstractIDEAction extends AbstractAction {

    public static final int CNTRL = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

    public AbstractIDEAction(String name){
        this(name, -1);
    }

    public AbstractIDEAction(String name, int key){
        this(name, key, false, false);
    }

    public AbstractIDEAction(String name, int key, boolean control, boolean shift){
        super(name);

        if(key != -1){
            int mode = 0;

            if(control)
                mode |= CNTRL;
            if(shift)
                mode |= InputEvent.SHIFT_MASK;

            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, mode));
        }
    }
}
