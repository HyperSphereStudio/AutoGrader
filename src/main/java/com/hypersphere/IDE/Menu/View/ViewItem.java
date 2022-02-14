package com.hypersphere.IDE.Menu.View;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.Menu.AbstractIDEMenu;
import org.fife.ui.autocomplete.CompletionCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewItem extends AbstractIDEMenu {

    private IDE ide;

    private AbstractChoice alternateRowColorsAction;
    private AbstractChoice descWindowAction;
    private AbstractChoice paramAssistanceAction;
    private AbstractChoice autoComplete;

    private static final Color ALT_BG_COLOR = new Color(0xf0f0f0);

    public ViewItem() {
        super("View");
    }

    @Override
    public void init(IDE ide) {
        super.init(ide);
        this.ide = ide;



        alternateRowColorsAction = new AbstractChoice("Alternate Row Colors in completion list", true) {
            @Override protected void _set(boolean b) { CompletionCellRenderer.setAlternateBackground(b ? ALT_BG_COLOR : null); }
        };

        descWindowAction = new AbstractChoice("Show Description", true) {
            @Override protected void _set(boolean b) { ide.getLangSupport().setShowDescWindow(b); }
        };

        paramAssistanceAction = new AbstractChoice("Parameter Assistance", true) {
            @Override protected void _set(boolean b) { ide.getLangSupport().setParameterAssistanceEnabled(b); }
        };

        autoComplete = new AbstractChoice("AutoComplete", true) {
            @Override protected void _set(boolean b) { ide.getLangSupport().setAutoCompleteEnabled(b); }
        };

    }

    public AbstractChoice getAlternateRowColors(){
        return alternateRowColorsAction;
    }
    public AbstractChoice getParamAssistance(){
        return paramAssistanceAction;
    }
    public AbstractChoice getShowDesc(){
        return descWindowAction;
    }
    public AbstractChoice getAutoComplete(){
        return autoComplete;
    }

    public abstract class AbstractChoice extends AbstractAction {

        protected final JCheckBoxMenuItem button;

        AbstractChoice(String name, boolean init_value){
            putValue(NAME, name);
            button = new JCheckBoxMenuItem(this);
            menu.add(button);
        }

        protected abstract void _set(boolean b);

        public void set(boolean b){
            button.setSelected(b);
            _set(b);
        }

        public boolean get(){
            return button.isSelected();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            set(get());
        }
    }


}
