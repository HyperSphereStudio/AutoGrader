package com.hypersphere.Analysis;

import com.hypersphere.Parse.CParser;
import com.hypersphere.Parse.CVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class Impl {

    public static abstract class AbstractCObject<T>{
        public abstract String toString(int block_idx);
        public String toString(){
            return toString(0);
        }
        public abstract T[] getChildren();
        public abstract int getObjectIdx();
    }

    public static class CDeclaration extends AbstractCObject{

        @Override
        public String toString(int block_idx) {
            return null;
        }

        @Override
        public AbstractCObject[] getChildren() {
            return new AbstractCObject[0];
        }

        @Override
        public int getObjectIdx() {
            return CParser.RULE_declaration;
        }
    }

    public static class CDeclarationList extends AbstractCObject<CDeclaration>{
        public CDeclaration[] declarations;
        @Override
        public String toString(int block_idx) {
            return null;
        }

        @Override
        public CDeclaration[] getChildren() {
            return declarations;
        }
        @Override
        public int getObjectIdx() {
            return CParser.RULE_declarationList;
        }
    }


    public static <T> T[] ArrayOf(ParserRuleContext ctx, CVisitor v, IntFunction<T[]> generator) {
        return Stream.of(ctx).map((Function<ParserRuleContext, Object>) v::visit).toArray(generator);
    }
}
