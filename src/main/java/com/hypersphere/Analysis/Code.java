package com.hypersphere.Analysis;

import com.hypersphere.Parse.CLexer;
import com.hypersphere.Parse.CParser;
import com.hypersphere.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import java.io.Reader;

public class Code{

    public Impl.Function[] functions;
    public Impl.Declarator[] declarations;
    public Object[] children;

    public Code(Reader r){
        try{
            CParser parser = new CParser(new CommonTokenStream(new CLexer(CharStreams.fromReader(r))));
            functions = Impl.ArrayOf(parser.functionDefinition(), Visitors.functionV, Impl.Function[]::new);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String toString(){
        return Utils.join((Object[]) functions);
    }


}


