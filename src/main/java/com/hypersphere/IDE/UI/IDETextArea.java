package com.hypersphere.IDE.UI;

import com.hypersphere.GUI.GUIChild;
import com.hypersphere.GUI.GUIFrame;
import com.hypersphere.IDE.CNTRL.IDEPanel;
import com.hypersphere.Utils;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.templates.StaticCodeTemplate;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class IDETextArea extends RSyntaxTextArea implements GUIChild<IDEPanel> {

    private static final ArrayList<String> templates = new ArrayList<>();
    private static final int START_ROWS = 40, START_COLS = 50;

    private IDEPanel ide;

    public IDETextArea(){
        super(START_ROWS, START_COLS);
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        Utils.println("\tInitializing TextBox");
        this.ide = ide;

        setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        setCodeFoldingEnabled(true);
        setMarkOccurrences(true);
        setTabsEmulated(true);
        setCaretPosition(0);
        setAutoIndentEnabled(true);
        setTabSize(3);

        ToolTipManager.sharedInstance().registerComponent(this);
        LanguageSupportFactory.get().register(this);

        getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) {}
            @Override public void removeUpdate(DocumentEvent e) { }
            @Override public void changedUpdate(DocumentEvent e) {
                ide.getManager().getActiveCode().setSourceCode(getText());
            }
        });

        setHyperlinksEnabled(true);
        addHyperlinkListener(e -> Utils.browseurl(e.getURL()));

        ide.getUi().getCsp().add(new RTextScrollPane(this));
        frame.getContentPane().add(new ErrorStrip(this), BorderLayout.LINE_END);

        final AtomicInteger sframeWidth = new AtomicInteger(0);
        final AtomicInteger sframeHeight = new AtomicInteger(0);

        frame.addComponentListener(new ComponentAdapter(){
            @Override public void componentResized(ComponentEvent e){
                 super.componentResized(e);

                 if(sframeWidth.get() == 0){
                     sframeWidth.set(frame.getWidth());
                     sframeHeight.set(frame.getHeight());
                 }

                 int lastCols = getColumns();
                 int lastRows = getRows();
                 int cols = (int) (START_COLS * ((double) frame.getWidth() / sframeWidth.get()));
                 int rows = (int) (START_ROWS * ((double) frame.getHeight() / sframeHeight.get()));
                 setColumns(Math.max(0, cols));
                 setRows(Math.max(0, rows));
                 System.out.println("\tResized Text Area [" + lastRows + "Rows, " + lastCols + "Cols] ["
                            + getRows() + "Rows, " + getColumns() + "Cols]");
                 validate();
            }
        });

        Utils.println("\tInitialized TextBox");
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide) {

    }

    public static String GetHelpString(){
        return GetTemplateHelpString() + "AutoComplete (Use Ctrl+space)\n";
    }

    private static String GetTemplateHelpString(){
        StringBuilder sb = new StringBuilder("Templates (Use with Ctrl+Shift+space)\n");
        for(String temp : templates)
            sb.append("\t").append(temp).append('\n');
        return sb.toString();
    }

    private static void add_template(String id, String before, String after){
        RSyntaxTextArea.getCodeTemplateManager().addTemplate(new StaticCodeTemplate(id, before, after));
        templates.add(id);
    }

    static{
        RSyntaxTextArea.setTemplatesEnabled(true);
        add_template("for", "for(int i = 0; i < ", "; ++i){\n\t\n}\n");
        add_template("while", "while(", "){\n\t\n}\n");
    }
}
