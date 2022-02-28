package com.hypersphere.IDE.Menu;

import com.hypersphere.GUI.GUIFrame;
import com.hypersphere.IDE.CNTRL.IDEPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LookAndFeelItem extends AbstractIDEMenu {

    private GUIFrame frame;

    public LookAndFeelItem(){
        super("Look");
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        super.init(frame, ide);
        this.frame = (GUIFrame) frame;
        ButtonGroup bg = new ButtonGroup();
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
        {
            Look look = new Look(ide, info);
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(look);
            bg.add(item);
            menu.add(item);
        }
    }

    class Look extends AbstractAction {

        private final UIManager.LookAndFeelInfo info;
        private final IDEPanel ide;

        Look(IDEPanel ide, UIManager.LookAndFeelInfo info) {
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
                frame.pack();
            } catch (RuntimeException re) {
                throw re; // FindBugs
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
