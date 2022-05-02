package com.hypersphere.IDE.CNTRL;

import com.hypersphere.Config.Configuration;
import com.hypersphere.Config.ConfigurationManager;
import com.hypersphere.IDE.Menu.ViewItem;

import javax.swing.*;

public class IDEConfiguration extends Configuration<IDEPanel> {

    private static final String WIDTH = "IDE_SCREEN_WIDTH", HEIGHT = "IDE_SCREEN_HEIGHT";

    private ViewItem view;
    private final IDEPanel panel;


    public IDEConfiguration(ConfigurationManager manager, IDEPanel panel) {
        super(manager);
        this.panel = panel;

    }

    public void setWidth(int width, boolean update){
        put(WIDTH, width);

        if(update)
            panel.setSize(width, panel.getHeight());
    }

    public int getWidth(){
        return get(WIDTH, panel.getTextArea().getWidth());
    }

    public void setHeight(int height, boolean update){
        put(HEIGHT, height);

        if(update)
            panel.setSize(panel.getWidth(), height);
    }

    public int getHeight(){
        return get(HEIGHT, panel.getTextArea().getHeight());
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        this.view = panel.getUi().getMenu().getView();
        super.init(frame, ide);

        getWidth();
        getHeight();
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide) {
        super.destroy(frame, ide);
    }


}
