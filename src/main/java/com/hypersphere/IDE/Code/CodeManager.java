package com.hypersphere.IDE.Code;

import com.hypersphere.IDE.CNTRL.IDEPanel;
import com.hypersphere.GUI.GUIChild;
import com.hypersphere.Utils;

import javax.swing.*;
import java.util.List;

public class CodeManager implements GUIChild<IDEPanel> {

    private final List<Code> codeList = Utils.SyncList();
    private final int activeIdx = 0;

    public CodeManager(){ }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        codeList.add(new Code(null));
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide) {
        codeList.forEach(Code::close);
    }

    public void addCode(Code c){
        if(!codeList.contains(c))
            codeList.add(c);
    }

    void closeCode(Code c){

        codeList.remove(c);
    }

    void updateCode(Code c){

    }

    public Code getActiveCode(){
        return codeList.get(activeIdx);
    }

    public int getActiveIdx(){
        return activeIdx;
    }

    public List<Code> getCodeList() {
        return codeList;
    }
}
