package com.hypersphere.IDE.CNTRL;

import com.hypersphere.GUI.GUIPanel;
import com.hypersphere.IDE.Code.CodeManager;
import com.hypersphere.Configuration;
import com.hypersphere.IDE.SpellChecker.IDESpellChecker;
import com.hypersphere.IDE.UI.IDEUI;
import com.hypersphere.Utils;
import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ui.CollapsibleSectionPanel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class IDEPanel extends GUIPanel<IDEPanel> {

    public static final File IDE_Dir = Utils.mk(new File(".hypersphere"), true);
    public static final List<IDEPanel> ides = Utils.SyncList();
    private final IDEUI ui;
    private final IDESpellChecker checker;
    private final CodeManager manager;
    private final Configuration config;

    public IDEPanel(){
        config = new Configuration();
        manager = new CodeManager();
        ui = new IDEUI();
        checker = new IDESpellChecker();
    }

    @Override
    public void init(JFrame frame, IDEPanel ide){
        super.init(frame, ide);
        Utils.println("Initializing IDE");
        manager.init(frame, this);
        ides.add(this);
        ui.init(frame, this);
        checker.init(frame, this);
        config.init(frame, this);

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                getTextArea().requestFocusInWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Utils.println("Initialized IDE");
    }

    public RSyntaxTextArea getTextArea(){
        return ui.getTextArea();
    }

    public IDEUI getUi() {
        return ui;
    }

    public CollapsibleSectionPanel getCSP(){
        return ui.getCsp();
    }

    public CodeManager getManager() {
        return manager;
    }

    public Configuration getConfig() {
        return config;
    }

    public LanguageSupport getLangSupport(){
        return (LanguageSupport) getTextArea().getClientProperty("org.fife.rsta.ac.LanguageSupport");
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide){
        super.destroy(frame, ide);
        Utils.println("Destroying IDE");

        ui.destroy(frame, this);
        checker.destroy(frame, this);
        ides.remove(this);
        manager.destroy(frame, this);
        config.destroy(frame, this);

        Utils.println("Destroyed IDE");
    }
}
