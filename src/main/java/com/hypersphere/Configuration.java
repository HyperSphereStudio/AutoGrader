package com.hypersphere;

import com.hypersphere.IDE.CNTRL.IDEPanel;
import com.hypersphere.GUI.GUIChild;
import com.hypersphere.IDE.Menu.ViewItem;

import javax.swing.*;
import java.io.*;

public class Configuration implements GUIChild<IDEPanel> {
    private static final File config_file;
    private static ConfigurationPOJO config;

    private IDEPanel ide;
    private ViewItem view;


    public static void save(){
        try(Writer w = new FileWriter(config_file)){
            Utils.println("Saving Configurations to \"" + config_file + "\"");
            Utils.toPrettyJson(w, config);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        this.ide = ide;

        if(ide != null){
            view = ide.getUi().getMenu().getView();

            setShowDesc(isShowDesc());
            setParamAssistance(isParamAssistance());
            setAutoComplete(isAutoComplete());
            setAlternateRowColors(isAlternateRowColors());
            setScreenWidth(getScreenWidth());
            setScreenHeight(getScreenHeight());
        }
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide) {
        save();
    }



    public boolean isShowDesc() {
        return config.showDesc;
    }

    public void setShowDesc(boolean showDesc) {
        view.getShowDesc().set(showDesc);
        config.showDesc = showDesc;
    }

    public boolean isParamAssistance() {
        return config.paramAssistance;
    }

    public void setParamAssistance(boolean paramAssistance) {
        view.getParamAssistance().set(paramAssistance);
        config.paramAssistance = paramAssistance;
    }

    public boolean isAlternateRowColors() {
        return config.alternateRowColors;
    }

    public void setAlternateRowColors(boolean alternateRowColors) {
        view.getAlternateRowColors().set(alternateRowColors);
        config.alternateRowColors = alternateRowColors;
    }

    public boolean isAutoComplete() {
        return config.autoComplete;
    }

    public void setAutoComplete(boolean autoComplete) {
        view.getAutoComplete().set(autoComplete);
        config.autoComplete = autoComplete;
    }

    public int getScreenWidth() {
        return config.screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        ide.setSize(screenWidth, config.screenHeight);
        config.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return config.screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        ide.setSize(config.screenWidth, screenHeight);
        config.screenHeight = screenHeight;
    }

    public void setProjectPath(String projectPath){
        config.projectPath = projectPath;
    }

    public String getProjectPath(){
        return config.projectPath == null ? "" : config.projectPath;
    }

    static{
        config_file = Utils.mk(new File(IDEPanel.IDE_Dir, "config.json"), false);
        Utils.println("Loading Configurations from \"" + config_file + "\"");
        try(Reader r = new FileReader(config_file)){
            config = Utils.fromJson(r, ConfigurationPOJO.class);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}class ConfigurationPOJO {

    public boolean showDesc = true;
    public boolean paramAssistance = true;
    public boolean alternateRowColors = true;
    public boolean autoComplete = true;

    public int screenWidth = 500;
    public int screenHeight = 500;
    public String projectPath = "";

    public ConfigurationPOJO() {}
}
