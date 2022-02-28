package com.hypersphere.IDE.Menu;

import com.hypersphere.IDE.CNTRL.IDEPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CompileItem extends AbstractIDEMenu {


    public CompileItem(){
        super("Compile");
    }

    public void compile(){
        StringBuilder sb = new StringBuilder();
        try{
            Process p = Runtime.getRuntime().exec("cmd.exe /c make V=1");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line).append('\n');
            if(p.exitValue() != 0){
                JOptionPane.showMessageDialog(null, "Compilation Failed:" + sb.toString());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    private void compileAndRun(){
        compile();
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        super.init(frame, ide);
        addItem(new Compile());
        addItem(new CompileAndRun());
    }

    private class Compile extends AbstractIDEAction {

        Compile() {
            super("Compile", KeyEvent.VK_Y, true, false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            compile();
        }
    }

    private class CompileAndRun extends AbstractIDEAction {

        CompileAndRun() {
            super("Compile And Run", KeyEvent.VK_R, true, false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            compileAndRun();
        }
    }
}
