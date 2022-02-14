package com.hypersphere.IDE.CNTRL;

import com.hypersphere.IDE.Code.CodeManager;
import com.hypersphere.Configuration;
import com.hypersphere.IDE.SpellChecker.IDESpellChecker;
import com.hypersphere.IDE.UI.IDEFrame;
import com.hypersphere.IDE.UI.IDEUI;
import com.hypersphere.Utils;
import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ui.CollapsibleSectionPanel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class IDE extends IDEFrame {

    public static final File IDE_Dir = Utils.mk(new File(".hypersphere"), true);
    public static final List<IDE> ides = Utils.SyncList();
    private final IDEUI ui;
    private final IDESpellChecker checker;
    private final CodeManager manager;
    private final Configuration config;

    public IDE(){
        super("Grader IDE");
        config = new Configuration();
        manager = new CodeManager();
        ui = new IDEUI();
        checker = new IDESpellChecker();
    }

    @Deprecated
    @Override
    public void init(IDE ide){
        throw new Error("Cannot Initialize IDE");
    }

    public void init() {
        Utils.println("Initializing IDE");
        manager.init(this);
        ides.add(this);
        ui.init(this);
        checker.init(this);
        config.init(this);

        setLocationRelativeTo(null);
        getToolkit().setDynamicLayout(true);

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                getTextArea().requestFocusInWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addWindowListener(new IDEListener(this));

        super.init(this);

        Utils.println("Initialized IDE");
    }

    public RSyntaxTextArea getTextArea(){
        return ui.getTextArea().getTextArea();
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

    public void destroy(){
        Utils.println("Destroying IDE");

        ui.destroy(this);
        checker.destroy(this);
        ides.remove(this);
        manager.destroy(this);
        config.destroy(this);

        Utils.println("Destroyed IDE");
    }
}
