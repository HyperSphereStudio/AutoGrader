package com.hypersphere.Analysis;

import com.hypersphere.Parse.CBaseVisitor;
import com.hypersphere.Parse.CLexer;
import com.hypersphere.Parse.CParser;
import com.hypersphere.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import java.io.Reader;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class Code{

    private static final FunctionV functionV = new FunctionV();
    private static final DeclaratorV declaratorV = new DeclaratorV();
    private static final ParameterTypeListV paramListV = new ParameterTypeListV();
    private static final ParameterDeclarationV paramDecV = new ParameterDeclarationV();

    public Function[] functions;

    public Code(Reader r){
        try{
            CParser parser = new CParser(new CommonTokenStream(new CLexer(CharStreams.fromReader(r))));

            functions = ArrayOf(parser.functionDefinition(), functionV, Function[]::new);



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String toString(){
        return Utils.join((Object[]) functions);
    }

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

    private static <T> T[] ArrayOf(ParserRuleContext ctx, ParseTreeVisitor v, IntFunction<T[]> generator) {
        return Stream.of(ctx).map(m -> m.accept(v)).toArray(generator);
    }


    private static class FunctionV extends CBaseVisitor<Function> {
        @Override public Function visitFunctionDefinition(CParser.FunctionDefinitionContext ctx){
            Function f = new Function();
            f.declaration = declaratorV.visitDeclarator(ctx.declarator());
            f.declaration.specifier = new Type(ctx.declarationSpecifiers().getText());
            f.declaration.isFunction = true;

            return f;
        }
    }

    private static class DeclaratorV extends CBaseVisitor<Declarator> {
        @Override public Declarator visitDeclarator(CParser.DeclaratorContext ctx2){
            CParser.DirectDeclaratorContext ctx = ctx2.directDeclarator();
            Declarator d = new Declarator();

            d.name = ctx.directDeclarator().Identifier().getText();
            d.paramList = ctx.parameterTypeList() != null ? paramListV.visitParameterTypeList(ctx.parameterTypeList()) : null;
            return d;
        }
    }

    private static class ParameterTypeListV extends CBaseVisitor<ParameterTypeList> {
        @Override public ParameterTypeList visitParameterTypeList(CParser.ParameterTypeListContext ctx){
            ParameterTypeList list = new ParameterTypeList();
            List<CParser.ParameterDeclarationContext> declaration = ctx.parameterList().parameterDeclaration();
            list.parameters = new Variable[declaration.size()];

            for(int i = 0; i < list.parameters.length; ++i)
                list.parameters[i] = declaration.get(i).accept(paramDecV);

            return list;
        }
    }

    private static class ParameterDeclarationV extends CBaseVisitor<Variable> {
        @Override public Variable visitParameterDeclaration(CParser.ParameterDeclarationContext ctx){
            Variable v = new Variable();
            v.name = ctx.declarator().getText();
            v.type = new Type(ctx.declarationSpecifiers().getText());
            return v;
        }
    }



}


