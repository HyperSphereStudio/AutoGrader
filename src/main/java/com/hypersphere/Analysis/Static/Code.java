package com.hypersphere.Analysis.Static;

import com.hypersphere.Parse.CLexer;
import com.hypersphere.Parse.CParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

public class Code extends AbstractCodeObject{

    public Impl.CDeclarationList declarationList;

    public Code(String s){
        this(new StringReader(s));
    }

    public Code(File f){
        this(getFileReader(f));
    }

    public static CParser getParser(Reader r){
        try {
            return new CParser(new CommonTokenStream(new CLexer(CharStreams.fromReader(r))));
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Code(Reader r){
        try{
            declarationList = new CImplVisitor().visit(getParser(r).translationUnit());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    String toString(int block_idx) {
        return join(block_idx, declarationList);
    }


    private static FileReader getFileReader(File f){
        try{
            return new FileReader(f);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}

abstract class AbstractCodeObject{

    public String toString(){
        return toString(0);
    }

    abstract String toString(int block_idx);

    public static <T> List<T> asList(T... o){
        return Arrays.asList(o);
    }

    public static String getString(int block_idx, Object o){
        return o == null ? "" :
                (o instanceof AbstractCodeObject ?
                        ((AbstractCodeObject) o).toString(block_idx) :
                        o.toString());
    }

    public static String join(int block_idx, Object... array){
        return join(block_idx, "", array);
    }

    public static String joins(int block_idx, Object... array){
        return join(block_idx, " ", array);
    }

    public static String join(int block_idx, String delim, Object... array){
        if(array == null || array.length == 0)
            return "";
        StringBuilder sb = new StringBuilder(array[0] == null ? "" : array[0].toString());
        for(int i = 1; i < array.length; ++i){
            sb.append(delim);
            if(array[i] != null)
                sb.append(getString(block_idx, array[i]));
        }
        return sb.toString();
    }


    public static String rep(int count){
        return rep("\t", count);
    }

    public static String rep(String s, int count){
        StringBuilder sb = new StringBuilder(s.length() * count);
        for(int i = 0; i < count; ++i)
            sb.append(s);
        return sb.toString();
    }
}


