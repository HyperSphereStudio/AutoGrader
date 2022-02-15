package com.hypersphere.Analysis;

import com.hypersphere.Utils;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import java.util.function.IntFunction;
import java.util.stream.Stream;

public class Impl {

    public static class Function extends AbstractCodeObject{
        public Declarator declaration;
        public Block block;

        String toString(int block_idx){
            return join(block_idx, declaration, block);
        }
    }

    public static class Declarator extends AbstractCodeObject{
        public String name;
        public Type specifier;
        public ParameterTypeList paramList;
        public boolean isFunction;

        String toString(int block_idx) {
            return rep(block_idx) + joins(block_idx, specifier, name) + (isFunction ? join(block_idx,'(', paramList, ")") : ";");
        }
    }

    public static abstract class Statement extends AbstractCodeObject{

        String toString(int block_idx) {
            return rep(block_idx);
        }

        public static class Expression extends Statement{
            public String name;
            String toString(int block_idx) {
                return super.toString(block_idx);
            }

            public static class Logical extends Expression{

            }

            public static class Math extends Expression{

            }

            public static class FunctionCall extends Expression{
                
            }
        }

        public static class VariableDeclaration extends Statement{
            public String name;
            public Type specifier;
            public Statement initStatement;

            String toString(int block_idx) {
                return rep(block_idx) + joins(block_idx, specifier, name) + getString(block_idx, initStatement);
            }
        }
    }

    public static class Block extends AbstractCodeObject{
        public Statement[] statements;

        String toString(int block_idx) {
            String tabs = rep(block_idx + 1);
            return Utils.join("{\n", tabs, join(block_idx, Utils.join(";", tabs, "\n"), (Object[]) statements), "}\n");
        }
    }

    public static class ParameterTypeList extends AbstractCodeObject{
        public Variable[] parameters;

        String toString(int block_idx) {
            return join(block_idx, ",", (Object[]) parameters);
        }
    }

    public static class Variable extends AbstractCodeObject{
        public Type type;
        public String name;

        String toString(int block_idx){
            return joins(block_idx, type, name);
        }
    }

    public static class Type extends AbstractCodeObject{
        public String name;
        public Type(String name){
            this.name = name;
        }

        String toString(int block_idx){
            return name;
        }
    }

    static <T> T[] ArrayOf(ParserRuleContext ctx, ParseTreeVisitor v, IntFunction<T[]> generator) {
        return Stream.of(ctx).map(m -> Visitors.Visit(m, v)).toArray(generator);
    }
}
