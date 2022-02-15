package com.hypersphere.Analysis;

import com.hypersphere.Parse.CBaseVisitor;
import com.hypersphere.Parse.CParser;
import com.hypersphere.Utils;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import java.util.List;

class Visitors {

    static final FunctionV functionV = new FunctionV();
    static final DeclaratorV declaratorV = new DeclaratorV();
    static final ParameterTypeListV paramListV = new ParameterTypeListV();
    static final ParameterDeclarationV paramDecV = new ParameterDeclarationV();
    static final BlockV blockV = new BlockV();
    static final VariableDeclarationV vdecv = new VariableDeclarationV();
    static final StatementV statementv = new StatementV();
    static final StatementV.ExpressionV exprV = new StatementV.ExpressionV();


    public static <T> T Visit(ParserRuleContext ctx, ParseTreeVisitor<T> visitor){
        return ctx != null ? ctx.accept(visitor) : null;
    }

    static class VariableDeclarationV extends CBaseVisitor<Impl.Statement.VariableDeclaration> {
        @Override public Impl.Statement.VariableDeclaration visitDeclaration(CParser.DeclarationContext ctx){
            return null;
        }
    }

    static class StatementV extends CBaseVisitor<Impl.Statement> {
        @Override public Impl.Statement visitStatement(CParser.StatementContext ctx2){
            if(ctx2.expressionStatement() != null){
                CParser.AssignmentExpressionContext ctx = ctx2.expressionStatement().expression()
                                                            .assignmentExpression(0);
                return Visit(ctx, exprV);
            }else{
                return null;
            }
        }

        static class ExpressionV extends CBaseVisitor<Impl.Statement.Expression> {
            @Override public Impl.Statement.Expression visitAssignmentExpression(CParser.AssignmentExpressionContext ctx){
                ctx.conditionalExpression()
                                 .logicalOrExpression()
                                 .logicalAndExpression(0)
                                 .inclusiveOrExpression(0)
                                 .exclusiveOrExpression(0)
                                 .andExpression(0)
                                 .equalityExpression(0)
                                 .relationalExpression(0)
                                 .shiftExpression(0)
                                 .additiveExpression(0)
                                 .multiplicativeExpression(0)
                                 .castExpression(0)
                                 .unaryExpression()
                                 .postfixExpression()   //Where The parameters Lie
                                 .primaryExpression()   //Function Name
                                 .getText();
                return null;
            }
        }
    }

    static class BlockV extends CBaseVisitor<Impl.Block> {
        @Override public Impl.Block visitBlockItemList(CParser.BlockItemListContext ctx){
            Impl.Block b = new Impl.Block();
            b.statements = ctx.blockItem().stream().map(i -> {
                if(i.declaration() != null){
                    return Visit(i.declaration(), vdecv);
                }else{
                    return Visit(i.statement(), statementv);
                }
            }).toArray(Impl.Statement[]::new);
            return b;
        }
    }

    static class FunctionV extends CBaseVisitor<Impl.Function> {
        @Override public Impl.Function visitFunctionDefinition(CParser.FunctionDefinitionContext ctx){
            Impl.Function f = new Impl.Function();
            f.declaration = declaratorV.visitDeclarator(ctx.declarator());
            f.declaration.specifier = new Impl.Type(ctx.declarationSpecifiers().getText());
            f.declaration.isFunction = true;
            f.block = Visit(ctx.compoundStatement().blockItemList(), blockV);
            return f;
        }
    }

    static class DeclaratorV extends CBaseVisitor<Impl.Declarator> {
        @Override public Impl.Declarator visitDeclarator(CParser.DeclaratorContext ctx2){
            CParser.DirectDeclaratorContext ctx = ctx2.directDeclarator();
            Impl.Declarator d = new Impl.Declarator();

            d.name = ctx.directDeclarator().Identifier().getText();
            d.paramList = Visit(ctx.parameterTypeList(), paramListV);
            return d;
        }
    }

    static class ParameterTypeListV extends CBaseVisitor<Impl.ParameterTypeList> {
        @Override public Impl.ParameterTypeList visitParameterTypeList(CParser.ParameterTypeListContext ctx){
            Impl.ParameterTypeList list = new Impl.ParameterTypeList();
            List<CParser.ParameterDeclarationContext> declaration = ctx.parameterList().parameterDeclaration();
            list.parameters = new Impl.Variable[declaration.size()];

            for(int i = 0; i < list.parameters.length; ++i)
                list.parameters[i] = Visit(declaration.get(i), paramDecV);

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
