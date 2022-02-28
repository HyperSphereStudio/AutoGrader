package com.hypersphere.IDE.Menu;

import com.hypersphere.IDE.CNTRL.IDEPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FileItem extends AbstractIDEMenu {

    private IDEPanel ide;


    public FileItem() {
        super("File");
        addItem(new AbstractIDEAction("Save", KeyEvent.VK_S, true, false){
            @Override
            public void actionPerformed(ActionEvent e) {
                ide.getManager().getActiveCode().save();
            }
        });

        addItem(new AbstractIDEAction("SaveAs", KeyEvent.VK_A, true, false){
            @Override
            public void actionPerformed(ActionEvent e) {
                ide.getManager().getActiveCode().saveAsPrompt();
            }
        });
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        super.init(frame, ide);
        this.ide = ide;
    }
}
