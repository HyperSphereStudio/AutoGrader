package com.hypersphere.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GUIFrame extends JFrame implements WindowListener {

    private static final AtomicInteger ACTIVE_FRAMES = new AtomicInteger(0);

    private GUIPanel panel;
    private int startFrameWidth = -1;
    private int startFrameHeight = -1;


    public GUIFrame(String title, GUIPanel panel){
        this.panel = panel;
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        if(panel != null)
            add(panel);

        init();
    }

    private void init(){
        addWindowListener(this);
        getToolkit().setDynamicLayout(true);

        addComponentListener(new ComponentAdapter(){
            @Override public void componentResized(ComponentEvent e){
                if(startFrameWidth != -1 && startFrameHeight != -1){
                    System.out.println("Frame Resize [" + getWidth() + ", " + getHeight() + "]");

                    invalidate();
                    validate();
                }
                super.componentResized(e);
            }
        });

        if(panel != null)
            panel.init(this, panel);

        pack();
        setVisible(true);
        requestFocusInWindow();

        synchronized(ACTIVE_FRAMES){
            ACTIVE_FRAMES.getAndIncrement();
        }

        startFrameWidth = getWidth();
        startFrameHeight = getHeight();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void dispose(){
        windowClosing(null);
        super.dispose();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(panel != null){
            panel.destroy(this, panel);
            panel = null;
            startFrameWidth = -1;
            startFrameHeight = -1;
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        synchronized(ACTIVE_FRAMES){
            if(ACTIVE_FRAMES.decrementAndGet() <= 0){
                System.out.println("Exiting Program!");
                System.exit(0);
            }
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public GUIPanel getPanel() {
        return panel;
    }
}
