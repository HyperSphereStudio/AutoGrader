package com.hypersphere.Analysis;

import com.hypersphere.Parse.CParser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

class Visitors {

    public static abstract class CVisitor<T, PT extends Impl.AbstractCObject, CT extends ParserRuleContext>{
        protected PT parent;
        public CVisitor(PT parent){
            this.parent = parent;
        }

        public abstract T _visit(CT ctx);
        public T visit(CT ctx){
            return ctx != null ? _visit(ctx) : null;
        }
    }

    public static class CDeclarationVisitor extends CVisitor<Impl.CDeclaration, Impl.AbstractCObject, CParser.DeclarationContext>{
        public CDeclarationVisitor(Impl.AbstractCObject parent) {
            super(parent);
        }
        public Impl.CDeclaration _visit(CParser.DeclarationContext ctx){
            return null;
        }
    }

    public static class CDeclarationListVisitor extends CVisitor<Impl.CDeclarationList, Impl.AbstractCObject, CParser.DeclarationListContext>{
        public CDeclarationListVisitor(Impl.AbstractCObject parent) {
            super(parent);
        }
        public Impl.CDeclarationList _visit(CParser.DeclarationListContext ctx){
            List<CParser.DeclarationContext> ctxs = ctx.declaration();
            Impl.CDeclarationList l = new Impl.CDeclarationList();
            l.declarations = new Impl.CDeclaration[ctxs.size()];
            CDeclarationVisitor dv = new CDeclarationVisitor(l);
            for(int i = 0; i < ctxs.size(); ++i)
                l.declarations[i] = dv.visit(ctxs.get(i));
            return l;
        }
    }

}
