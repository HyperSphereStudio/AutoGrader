package com.hypersphere.Analysis;

import com.hypersphere.Utils;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import java.util.function.IntFunction;
import java.util.stream.Stream;

public class Impl {



    public static class Function{
        public Declarator declaration;

        public String toString(){
            return declaration + "{\n}\n";
        }
    }

    public static class Declarator{
        public String name;
        public Type specifier;
        public ParameterTypeList paramList;
        public boolean isFunction;

        public String toString() {
            return Utils.joins(specifier, name) + (isFunction ? Utils.join('(', paramList, ")") : ";");
        }
    }

    public static class ParameterTypeList{
        public Variable[] parameters;

        public String toString() {
            return Utils.join(",", parameters);
        }
    }

    public static class Variable{
        public Type type;
        public String name;

        public String toString(){
            return Utils.joins(type, name);
        }
    }

    public static class Type{
        public String name;
        public Type(String name){
            this.name = name;
        }

        public String toString(){
            return name;
        }
    }

    static <T> T[] ArrayOf(ParserRuleContext ctx, ParseTreeVisitor v, IntFunction<T[]> generator) {
        return Stream.of(ctx).map(m -> m.accept(v)).toArray(generator);
    }
}
