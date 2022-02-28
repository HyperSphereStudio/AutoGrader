package com.hypersphere.IDE.Menu;

import com.hypersphere.IDE.CNTRL.IDEPanel;
import com.hypersphere.IDE.UI.IDETextArea;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AboutItem extends AbstractIDEMenu {

    public AboutItem() {
        super("About");
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        super.init(frame, ide);
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
