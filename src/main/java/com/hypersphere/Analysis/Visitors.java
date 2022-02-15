package com.hypersphere.Analysis;

import com.hypersphere.Parse.CBaseVisitor;
import com.hypersphere.Parse.CParser;

import java.util.List;

class Visitors {

    static final FunctionV functionV = new FunctionV();
    static final DeclaratorV declaratorV = new DeclaratorV();
    static final ParameterTypeListV paramListV = new ParameterTypeListV();
    static final ParameterDeclarationV paramDecV = new ParameterDeclarationV();

    static class FunctionV extends CBaseVisitor<Impl.Function> {
        @Override public Impl.Function visitFunctionDefinition(CParser.FunctionDefinitionContext ctx){
            Impl.Function f = new Impl.Function();
            f.declaration = declaratorV.visitDeclarator(ctx.declarator());
            f.declaration.specifier = new Impl.Type(ctx.declarationSpecifiers().getText());
            f.declaration.isFunction = true;

            return f;
        }
    }

    static class DeclaratorV extends CBaseVisitor<Impl.Declarator> {
        @Override public Impl.Declarator visitDeclarator(CParser.DeclaratorContext ctx2){
            CParser.DirectDeclaratorContext ctx = ctx2.directDeclarator();
            Impl.Declarator d = new Impl.Declarator();

            d.name = ctx.directDeclarator().Identifier().getText();
            d.paramList = ctx.parameterTypeList() != null ? paramListV.visitParameterTypeList(ctx.parameterTypeList()) : null;
            return d;
        }
    }

    static class ParameterTypeListV extends CBaseVisitor<Impl.ParameterTypeList> {
        @Override public Impl.ParameterTypeList visitParameterTypeList(CParser.ParameterTypeListContext ctx){
            Impl.ParameterTypeList list = new Impl.ParameterTypeList();
            List<CParser.ParameterDeclarationContext> declaration = ctx.parameterList().parameterDeclaration();
            list.parameters = new Impl.Variable[declaration.size()];

            for(int i = 0; i < list.parameters.length; ++i)
                list.parameters[i] = declaration.get(i).accept(paramDecV);

            return list;
        }
    }

    static class ParameterDeclarationV extends CBaseVisitor<Impl.Variable> {
        @Override public Impl.Variable visitParameterDeclaration(CParser.ParameterDeclarationContext ctx){
            Impl.Variable v = new Impl.Variable();
            v.name = ctx.declarator().getText();
            v.type = new Impl.Type(ctx.declarationSpecifiers().getText());
            return v;
        }
    }
}
