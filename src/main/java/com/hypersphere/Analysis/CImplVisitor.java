package com.hypersphere.Analysis;

import com.hypersphere.Parse.CBaseVisitor;
import com.hypersphere.Parse.CParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CImplVisitor extends AbstractCVisitor<Impl.CDeclarationList> {

    private static final DeclarationVisitor DECL_VIS = new DeclarationVisitor();
    private static final TypeVisitor TYPE_VIS = new TypeVisitor();
    private static final BlockVisitor BLOCK_VIS = new BlockVisitor();
    private static final ExpressionVisitor EXPR_VIS = new ExpressionVisitor();

    @Override
    public Impl.CDeclarationList visitTranslationUnit(CParser.TranslationUnitContext ctx) {
        Impl.CDeclarationList list = new Impl.CDeclarationList();
        list.declarations = new ArrayList<>();
        for (CParser.ExternalDeclarationContext ectx : ctx.externalDeclaration())
            list.declarations.add(DECL_VIS.visit(ectx));
        return list;
    }

    private static class TypeVisitor extends AbstractCVisitor<Impl.CType> {
        @Override
        public Impl.CType visitTypeSpecifier(CParser.TypeSpecifierContext ctx) {
            Impl.CType type = new Impl.CType();
            type.setType(ctx.getText());
            return type;
        }
    }

    private static class ExpressionVisitor extends AbstractCVisitor<Impl.CExpression> {
        @Override
        public Impl.CExpression visitStatement(CParser.StatementContext ctx) {
            return null;
        }
    }

    private static class BlockVisitor extends AbstractCVisitor<Impl.CBlock>{
        @Override
        public Impl.CBlock visitCompoundStatement(CParser.CompoundStatementContext ctx){
            Impl.CBlock block = new Impl.CBlock();
            if(ctx.blockItemList() != null){
                List<Impl.CExpression> expressions = new ArrayList<>();
                for(CParser.BlockItemContext item : ctx.blockItemList().blockItem()){
                    if(item.statement() == null){
                        Impl.CExpression.VariableDeclarationExpression v = new Impl.CExpression.VariableDeclarationExpression();
                        v.setVariable((Impl.CDeclaration.Variable) DECL_VIS.visit(item.declaration()));
                        expressions.add(v);
                    }else{
                        expressions.add(EXPR_VIS.visit(item.statement()));
                    }
                }
                block.setExpressionList(expressions);
            }
            return block;
        }
    }



    private static class DeclarationVisitor extends AbstractCVisitor<Impl.CDeclaration> {

        @Override
        public Impl.CDeclaration.Variable visitDeclaration(CParser.DeclarationContext ctx){
            Impl.CDeclaration.Variable v = new Impl.CDeclaration.Variable();

            v.setType(TYPE_VIS.visit(
                    BroadcastSing(ctx.declarationSpecifiers(),
                            (Function<CParser.DeclarationSpecifierContext, CParser.TypeSpecifierContext>)
                                    x -> x.typeSpecifier())));

            return v;
        }

        @Override
        public Impl.CDeclaration.Variable visitParameterDeclaration(CParser.ParameterDeclarationContext ctx){
            Impl.CDeclaration.Variable v = new Impl.CDeclaration.Variable();
            CParser.DirectDeclaratorContext ctx2 = ctx.declarator().directDeclarator();

            v.setName(ctx2.Identifier().getText());
            v.setType(TYPE_VIS.visit(ctx2.typeSpecifier()));
            return v;
        }


        @Override
        public Impl.CDeclaration.Function visitFunctionDefinition(CParser.FunctionDefinitionContext ctx) {
            Impl.CDeclaration.Function fun = new Impl.CDeclaration.Function();
            CParser.DirectDeclaratorContext dec = ctx.declarator().directDeclarator();
            CParser.ParameterTypeListContext ptctx = dec.parameterTypeList();

            fun.setReturn_type(TYPE_VIS.visit(
                    BroadcastSing(ctx.declarationSpecifiers(),
                    (Function<CParser.DeclarationSpecifierContext, CParser.TypeSpecifierContext>)
                            x -> x.typeSpecifier())));

            fun.setName(dec.directDeclarator().Identifier().getText());

            if(ptctx != null)
                fun.setArguments(Broadcast(ptctx.parameterList(),
                        (Function<CParser.ParameterDeclarationContext, Impl.CDeclaration.Variable>)
                                x -> visitParameterDeclaration(x)));

            fun.setBlock(BLOCK_VIS.visit(ctx.compoundStatement()));
            return fun;
        }

        @Override
        public Impl.CDeclaration visitExternalDeclaration(CParser.ExternalDeclarationContext ctx) {
            Impl.CDeclaration d = visit(ctx.functionDefinition());
            if (d != null)
                return d;
            return visit(ctx.declaration());
        }
    }



    private interface BoolFunction<T> {
        boolean select(T t);
    }
    private static <T> T SelectSingle(ParseTree tree, BoolFunction<T> selector){
        if(tree == null)
            return null;
        for (int i = 0; i < tree.getChildCount(); ++i) {
            if (selector.select((T) tree.getChild(i)))
                return (T) tree.getChild(i);
        }
        return null;
    }
    private static <T> List<T> Select(ParseTree tree, BoolFunction<T> selector) {
        if(tree == null)
            return null;
        List<T> l = new ArrayList<>();
        for (int i = 0; i < tree.getChildCount(); ++i) {
            if (selector.select((T) tree.getChild(i)))
                l.add((T) tree.getChild(i));
        }
        return l;
    }
    private static <T, K> List<T> Broadcast(ParseTree tree, Function<K, T> mapper) {
        if(tree == null)
            return null;
        List<T> l = new ArrayList<>();
        for (int i = 0; i < tree.getChildCount(); ++i) {
            T t = mapper.apply((K) tree.getChild(i));
            if(t != null)
                l.add(t);
        }
        return l;
    }
    private static <T, K> T BroadcastSing(ParseTree tree, Function<K, T> mapper){
        if(tree == null)
            return null;
        for (int i = 0; i < tree.getChildCount(); ++i) {
            T t = mapper.apply((K) tree.getChild(i));
            if(t != null)
                return t;
        }
        return null;
    }
}

class AbstractCVisitor<T> extends CBaseVisitor<T>{

    @Override
    public T visit(ParseTree tree){
        return tree != null ? super.visit(tree) : null;
    }
}