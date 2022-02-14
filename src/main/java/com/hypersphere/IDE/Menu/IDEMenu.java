package com.hypersphere.IDE.Menu;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.IDEObj;
import com.hypersphere.IDE.Menu.About.AboutItem;
import com.hypersphere.IDE.Menu.Compile.CompileItem;
import com.hypersphere.IDE.Menu.File.FileItem;
import com.hypersphere.IDE.Menu.Look.LookAndFeelItem;
import com.hypersphere.IDE.Menu.Search.SearchItem;
import com.hypersphere.IDE.Menu.Settings.SettingsItem;
import com.hypersphere.IDE.Menu.View.ViewItem;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class IDEMenu implements IDEObj {

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
    public void init(IDE ide) {
        ide.setJMenuBar(mb);
        items.forEach(menu -> menu.init(ide));
    }

    @Override
    public void destroy(IDE ide) {
        items.forEach(menu -> menu.destroy(ide));
    }
}
