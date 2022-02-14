package com.hypersphere.IDE.Menu.File;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.Menu.AbstractIDEAction;
import com.hypersphere.IDE.Menu.AbstractIDEMenu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FileItem extends AbstractIDEMenu {

    private IDE ide;


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
    public void init(IDE ide) {
        super.init(ide);
        this.ide = ide;
    }
}
