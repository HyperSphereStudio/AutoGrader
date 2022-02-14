package com.hypersphere.IDE.Code;

import com.hypersphere.IDE.CNTRL.IDE;
import com.hypersphere.IDE.IDEObj;
import com.hypersphere.Utils;

import java.util.List;

public class CodeManager implements IDEObj {

    private final List<Code> codeList = Utils.SyncList();
    private final int activeIdx = 0;

    public CodeManager(){ }

    @Override
    public void init(IDE ide) {
        codeList.add(new Code(null));
    }

    @Override
    public void destroy(IDE ide) {
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
