package com.hypersphere.Config;

import com.hypersphere.GUI.GUIChild;
import com.hypersphere.GUI.GUIPanel;

import javax.swing.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Configuration<P extends GUIPanel> implements GUIChild<P> {

    protected final ConfigurationManager manager;

    public Configuration(ConfigurationManager manager) {
        this.manager = manager;
    }



    public <T> T get(String name) {
        return (T) manager.get(name);
    }
    public <T> T get(String name, T def) {
        return (T) manager.getOrDefault(name, def);
    }

    public void put(String name, Object o){
        manager.put(name, o);
    }

    public void addListener(String name, Consumer<Object> consumer){
        manager.addListener(name, consumer);
    }

    @Override
    public void init(JFrame frame, P panel) {

    }

    @Override
    public void destroy(JFrame frame, P panel) {

    }

    public ConfigurationManager getManager(){
        return manager;
    }
}