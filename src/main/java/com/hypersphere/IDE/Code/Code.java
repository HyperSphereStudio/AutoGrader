package com.hypersphere.IDE.Code;

import com.hypersphere.GUI.GUIChild;
import com.hypersphere.IDE.CNTRL.IDEPanel;
import com.hypersphere.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class Code implements GUIChild<IDEPanel> {

    private final List<CodeManager> managers = Utils.SyncList();
    private final Object wrloc = new Object();
    private File sourceFile;
    private StringBuilder sourceCode;
    private boolean isDirty;

    public Code(File f) {
        sourceFile = f;
        if (sourceFile != null)
            changeSourceCode(Utils.ReadFile(f));
    }

    public void move(File newLocation) {
        if (newLocation != sourceFile) {
            synchronized (wrloc) {
                sourceFile.renameTo(newLocation);
                sourceFile = newLocation;
            }
        }
    }

    public void saveAsPrompt() {
        try {
            synchronized (wrloc) {
                String userProjectSavePath;
                if((userProjectSavePath = Utils.RequestFile(true, FileDialog.SAVE)) != null){
                    sourceFile = new File(userProjectSavePath);
                    if (!sourceFile.exists())
                        sourceFile.createNewFile();
                    save();
                    Utils.println("Saved Code File \"" + userProjectSavePath + "\"");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        if (sourceFile == null) {
            saveAsPrompt();
        } else {
            if (isDirty) {
                synchronized (wrloc) {
                    Utils.WriteFile(getSourceCode(), sourceFile);
                }
                isDirty = false;
            }
        }
    }

    public void close() {
        managers.forEach(manager -> manager.closeCode(this));
    }

    public void delete() {
        synchronized (wrloc) {
            if (sourceFile.exists())
                sourceFile.delete();
        }
    }

    public String getSourceCode() {
        synchronized (wrloc) {
            return sourceCode.toString();
        }
    }

    public void setSourceCode(String sourceCode) {
        synchronized (wrloc) {
            isDirty = true;
            changeSourceCode(sourceCode);
            managers.forEach(manager -> manager.updateCode(this));
        }
    }

    void changeSourceCode(String code) {
        this.sourceCode = new StringBuilder(code);
    }

    public List<CodeManager> getManagers() {
        return managers;
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        managers.add(ide.getManager());
        ide.getManager().addCode(this);
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide) {
        ide.getManager().closeCode(this);
    }
}
