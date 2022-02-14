package com.hypersphere.IDE.Menu.Search;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.Menu.AbstractIDEAction;
import com.hypersphere.IDE.Menu.AbstractIDEMenu;
import com.hypersphere.IDE.UI.StatusBar;
import org.fife.rsta.ui.GoToDialog;
import org.fife.rsta.ui.search.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SearchItem extends AbstractIDEMenu implements SearchListener {

    private IDE ide;
    private FindDialog findDialog;
    private ReplaceDialog replaceDialog;
    private final FindToolBar findToolBar;
    private final ReplaceToolBar replaceToolBar;
    private final StatusBar statusBar;

    public SearchItem(){
        super("Search");
        statusBar = new StatusBar();
        findToolBar = new FindToolBar(this);
        replaceToolBar = new ReplaceToolBar(this);
    }

    public void updateUI(){
        if(findDialog != null){
            findDialog.updateUI();
            replaceDialog.updateUI();
        }
    }

    public ReplaceDialog getReplaceDialog() {
        return replaceDialog;
    }

    public FindToolBar getFindToolBar() {
        return findToolBar;
    }

    @Override
    public String getSelectedText() {
        return ide.getTextArea().getSelectedText();
    }

    /**
     * Listens for events from our search dialogs and actually does the dirty
     * work.
     */
    @Override
    public void searchEvent(SearchEvent e) {

        SearchEvent.Type type = e.getType();
        SearchContext context = e.getSearchContext();
        SearchResult result;

        switch (type) {
            default: // Prevent FindBugs warning later
            case MARK_ALL:
                result = SearchEngine.markAll(ide.getTextArea(), context);
                break;
            case FIND:
                result = SearchEngine.find(ide.getTextArea(), context);
                if (!result.wasFound() || result.isWrapped()) {
                    UIManager.getLookAndFeel().provideErrorFeedback(ide.getTextArea());
                }
                break;
            case REPLACE:
                result = SearchEngine.replace(ide.getTextArea(), context);
                if (!result.wasFound() || result.isWrapped()) {
                    UIManager.getLookAndFeel().provideErrorFeedback(ide.getTextArea());
                }
                break;
            case REPLACE_ALL:
                result = SearchEngine.replaceAll(ide.getTextArea(), context);
                JOptionPane.showMessageDialog(null, result.getCount() +
                        " occurrences replaced.");
                break;
        }

        String text;
        if (result.wasFound()) {
            text = "Text found; occurrences marked: " + result.getMarkedCount();
        }
        else if (type==SearchEvent.Type.MARK_ALL) {
            if (result.getMarkedCount()>0) {
                text = "Occurrences marked: " + result.getMarkedCount();
            }
            else {
                text = "";
            }
        }
        else {
            text = "Text not found";
        }
        statusBar.setLabel(text);

    }

    @Override
    public void init(IDE ide) {
        super.init(ide);
        this.ide = ide;
        findDialog = new FindDialog(ide, this);
        replaceDialog = new ReplaceDialog(ide, this);
        statusBar.init(ide);

        SearchContext context = findDialog.getSearchContext();
        replaceDialog.setSearchContext(context);
        findToolBar.setSearchContext(context);
        replaceToolBar.setSearchContext(context);

        addItem(new ShowFindDialogAction());
        addItem(new ShowReplaceDialogAction());
        addItem(new GoToLineAction());
        menu.addSeparator();

        int ctrl = ide.getToolkit().getMenuShortcutKeyMask();
        int shift = InputEvent.SHIFT_MASK;
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F, ctrl|shift);
        Action a = ide.getCSP().addBottomComponent(ks, findToolBar);
        a.putValue(Action.NAME, "Find Toolbar");
        addItem(a);

        ks = KeyStroke.getKeyStroke(KeyEvent.VK_H, ctrl|shift);
        a = ide.getCSP().addBottomComponent(ks, replaceToolBar);
        a.putValue(Action.NAME, "Replace Toolbar");
        addItem(a);
    }

    private class GoToLineAction extends AbstractIDEAction {

        GoToLineAction() {
            super("Go To Line...", KeyEvent.VK_G, true, false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (findDialog.isVisible()) {
                findDialog.setVisible(false);
            }
            if (replaceDialog.isVisible()) {
                replaceDialog.setVisible(false);
            }
            RSyntaxTextArea textArea = ide.getTextArea();
            GoToDialog dialog = new GoToDialog(ide);
            dialog.setMaxLineNumberAllowed(ide.getTextArea().getLineCount());
            dialog.setVisible(true);
            int line = dialog.getLineNumber();
            if (line>0) {
                try {
                    textArea.setCaretPosition(textArea.getLineStartOffset(line-1));
                } catch (BadLocationException ble) {
                    UIManager.getLookAndFeel().provideErrorFeedback(textArea);
                    ble.printStackTrace();
                }
            }
        }

    }

    private class ShowReplaceDialogAction extends AbstractIDEAction {

        ShowReplaceDialogAction() {
            super("Replace...", KeyEvent.VK_H, true, false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (findDialog.isVisible()) {
                findDialog.setVisible(false);
            }
            replaceDialog.setVisible(true);
        }

    }

    private class ShowFindDialogAction extends AbstractIDEAction {

        ShowFindDialogAction() {
            super("Find...", KeyEvent.VK_H, true, false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (replaceDialog.isVisible()) {
                replaceDialog.setVisible(false);
            }
            findDialog.setVisible(true);
        }

    }


}
