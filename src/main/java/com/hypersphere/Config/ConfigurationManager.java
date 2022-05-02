package com.hypersphere.Config;

import com.hypersphere.GUI.GUIPanel;
import com.hypersphere.GUI.GUIChild;
import com.hypersphere.Utils;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConfigurationManager<T extends GUIPanel> extends HashMap<String, Object> implements GUIChild<T> {
    private final transient File config_file;
    private final transient DefaultConfiguration defaultConfig;
    private transient final List<BiConsumer<String, Object>> listeners = new ArrayList<>();

    public ConfigurationManager(){
        this(Utils.mk(new File(".hypersphere/config.json"), false));
    }

    public ConfigurationManager(File config_file){
        this.config_file = config_file;
        this.defaultConfig = new DefaultConfiguration(this);
    }

    public void save(){
        try(Writer w = new FileWriter(config_file)){
            Utils.println("Saving Configurations to \"" + config_file + "\"");
            Utils.toPrettyJson(w, this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init(JFrame frame, T panel) {
        if(config_file.exists()){
            try(FileReader reader = new FileReader(config_file)){
                putAll(Utils.fromJson(reader, ConfigurationManager.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        defaultConfig.init(frame, panel);
    }

    @Override
    public Object put(String name, Object o){
        for(BiConsumer<String, Object> bic : listeners)
            bic.accept(name, o);
        o = super.put(name, o);
        save();
        return o;
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue){
        Object res = super.getOrDefault(key, defaultValue);
        if(res == null && defaultValue != null)
            put((String) key, defaultValue);
        return res;
    }

    @Override
    public void destroy(JFrame frame, T panel) {
        defaultConfig.destroy(frame, panel);
        save();
    }

    public void addListener(String name, Consumer<Object> listener){
        BiConsumer<String, Object> listener2 = (n, o) -> {
            if(n.equals(name))
                listener.accept(o);
        };
        listeners.add(listener2);
        for(Entry<String, Object> entry : this.entrySet())
            listener2.accept(entry.getKey(), entry.getValue());
    }

    public File getConfigFile(){
        return config_file;
    }

    public DefaultConfiguration getDefaultConfig(){
        return defaultConfig;
    }

    public boolean exists(){
        return config_file.exists();
    }

}