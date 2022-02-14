package com.hypersphere.IDE.UI;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.IDEObj;
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
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.util.ArrayList;

public class IDETextArea implements IDEObj{

    private static final ArrayList<String> templates = new ArrayList<>();
    private final RSyntaxTextArea textArea;
    private IDE ide;

    public IDETextArea(){
        textArea = new RSyntaxTextArea(20, 60);
    }

    public RSyntaxTextArea getTextArea() {
        return textArea;
    }

    @Override
    public void init(IDE ide) {
        Utils.println("\tInitializing TextBox");
        this.ide = ide;

        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        textArea.setCodeFoldingEnabled(true);
        textArea.setMarkOccurrences(true);
        textArea.setTabsEmulated(true);
        textArea.setCaretPosition(0);
        textArea.setAutoIndentEnabled(true);
        textArea.setTabSize(3);

        ToolTipManager.sharedInstance().registerComponent(textArea);
        LanguageSupportFactory.get().register(textArea);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) {}
            @Override public void removeUpdate(DocumentEvent e) { }
            @Override public void changedUpdate(DocumentEvent e) {
                ide.getManager().getActiveCode().setSourceCode(textArea.getText());

            }
        });

        textArea.setHyperlinksEnabled(true);
        textArea.addHyperlinkListener(e -> Utils.browseurl(e.getURL()));

        ide.getUi().getCsp().add(new RTextScrollPane(textArea));
        ide.getContentPane().add(new ErrorStrip(textArea), BorderLayout.LINE_END);

        Utils.println("\tInitialized TextBox");
    }

    @Override
    public void destroy(IDE ide) {

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
