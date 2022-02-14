package com.hypersphere.IDE.Menu.Look;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.Menu.AbstractIDEMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LookAndFeelItem extends AbstractIDEMenu {

    public LookAndFeelItem(){
        super("Look");
    }

    @Override
    public void init(IDE ide) {
        super.init(ide);
        ButtonGroup bg = new ButtonGroup();
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
        {
            Look look = new Look(ide, info);
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(look);
            bg.add(item);
            menu.add(item);
        }
    }

}class Look extends AbstractAction {

    private final UIManager.LookAndFeelInfo info;
    private final IDE ide;

    Look(IDE ide, UIManager.LookAndFeelInfo info) {
        this.info = info;
        this.ide = ide;
        putValue(NAME, info.getName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            UIManager.setLookAndFeel(info.getClassName());
            SwingUtilities.updateComponentTreeUI(ide);
            ide.getUi().getMenu().getSearch().updateUI();
            ide.pack();
        } catch (RuntimeException re) {
            throw re; // FindBugs
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
