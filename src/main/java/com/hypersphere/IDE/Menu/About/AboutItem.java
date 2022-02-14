package com.hypersphere.IDE.Menu.About;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.Menu.AbstractIDEAction;
import com.hypersphere.IDE.Menu.AbstractIDEMenu;
import com.hypersphere.IDE.UI.IDETextArea;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AboutItem extends AbstractIDEMenu {

    public AboutItem() {
        super("About");
    }

    @Override
    public void init(IDE ide) {
        super.init(ide);
        addItem(new AbstractIDEAction("About", -1, true, false){
            @Override public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "C IDE built by Johnathan Bizzano");
            }
        });

        addItem(new AbstractIDEAction("Help", -1, true, false){
            @Override public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        IDETextArea.GetHelpString());
            }
        });
    }


}
