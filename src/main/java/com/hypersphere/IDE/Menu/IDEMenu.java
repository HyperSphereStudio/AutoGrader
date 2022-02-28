package com.hypersphere.IDE.Menu;

import com.hypersphere.IDE.CNTRL.IDEPanel;
import com.hypersphere.GUI.GUIChild;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class IDEMenu implements GUIChild<IDEPanel> {

    private final List<AbstractIDEMenu> items;

    private final JMenuBar mb;

    public IDEMenu(){
        mb = new JMenuBar();
        items = Arrays.asList(
                    new FileItem(),
                    new ViewItem(),
                    new SearchItem(),
                    new LookAndFeelItem(),
                    new CompileItem(),
                    new SettingsItem(),
                    new AboutItem()
            );
    }

    public SearchItem getSearch() {
        return (SearchItem) items.get(2);
    }

    public ViewItem getView(){
        return (ViewItem) items.get(1);
    }

    public JMenuBar getMenuBar() {
        return mb;
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        frame.setJMenuBar(mb);
        items.forEach(menu -> menu.init(frame, ide));
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide) {
        items.forEach(menu -> menu.destroy(frame, ide));
    }
}
