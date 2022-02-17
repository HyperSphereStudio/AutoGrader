package com.hypersphere.Analysis;

import com.hypersphere.Parse.CVisitor;
import com.hypersphere.Utils;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class Impl {


    public static abstract class AbstractCObject extends AbstractCodeObject{
        public String toString(){
            return toString(0);
        }
        public abstract List getChildren();
    }

    public static abstract class Value extends AbstractCObject{
        public abstract Object getJValue();
        public abstract void setJValue(Object o);
        public abstract char getBytes();
        public abstract void setBytes(char b);

        public static class Int extends Value{
            private long value;
            private char bytes;
            private boolean isSigned;

            public Int(long value, boolean signed){
                this.value = value;
                this.bytes = Long.BYTES;
                this.isSigned = signed;
            }

            public Int(int value, boolean signed){
                this.value = value;
                this.bytes = Integer.BYTES;
                this.isSigned = signed;
            }

            public Int(short value, boolean signed){
                this.value = value;
                this.bytes = Short.BYTES;
                this.isSigned = signed;
            }

            public Int(byte value, boolean signed){
                this.value = value;
                this.bytes = Byte.BYTES;
                this.isSigned = signed;
            }

            public Int(long value){
                this(value, false);
            }

            public Int(int value){
                this(value, false);
            }

            public Int(short value){
                this(value, false);
            }

            public Int(byte value){
                this(value, false);
            }

            public Int(char value){
                this.value = value;
                this.isSigned = true;
                this.bytes = Character.BYTES;
            }

            @Override
            String toString(int block_idx) {
                if(isSigned){
                    return String.valueOf(value);
                }else{
                    switch(bytes){
                        case 1:
                            return Long.toUnsignedString(Byte.toUnsignedLong((byte) value));
                        case 2:
                            return Long.toUnsignedString(Short.toUnsignedLong((short) value));
                        case 3:
                            return Long.toUnsignedString(Integer.toUnsignedLong((int) value));
                        default:
                            return Long.toUnsignedString(value);
                    }
                }
            }

            @Override
            public List getChildren() {
                return null;
            }

            @Override
            public Object getJValue() {
                return value;
            }

            @Override
            public void setJValue(Object o) {
                value = (int) o;
            }

            @Override
            public char getBytes() {
                return bytes;
            }

            @Override
            public void setBytes(char bytes) {
                this.bytes = bytes;
            }

            public long getValue() {
                return value;
            }

            public void setValue(long value) {
                this.value = value;
            }

            public boolean isSigned() {
                return isSigned;
            }

            public void setSigned(boolean signed) {
                isSigned = signed;
            }
        }
        public static class Float extends Value{
            private double value;
            private char bytes;

            public Float(double value){
                this.value = value;
                this.bytes = Double.BYTES;
            }

            public Float(float value){
                this.value = value;
                this.bytes = java.lang.Float.BYTES;
            }

            @Override
            String toString(int block_idx) {
                return null;
            }

            @Override
            public List getChildren() {
                return null;
            }

            @Override
            public Object getJValue() {
                return value;
            }

            @Override
            public void setJValue(Object o) {
                value = (double) o;
            }

            @Override
            public char getBytes() {
                return bytes;
            }

            @Override
            public void setBytes(char b) {
                bytes = b;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }
        }
        public static class CString extends Value{
            private String value;

            public CString(String value){
                this.value = value;
            }

            @Override
            String toString(int block_idx) {
                return "\"" + value + "\"";
            }

            @Override
            public List getChildren() {
                return null;
            }

            @Override
            public Object getJValue() {
                return value;
            }

            @Override
            public void setJValue(Object o) {
                value = (String) o;
            }

            @Override
            public char getBytes() {
                return (char) value.length();
            }

            @Override
            public void setBytes(char b) {
                throw new Error("Cannot Set Bytes of String");
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
    public static class CType extends AbstractCObject {
        private Value.CString type;

        @Override
        public String toString(int block_idx) {
            return type.toString();
        }

        @Override
        public List getChildren() {
            return null;
        }

        public Value.CString getType() {
            return type;
        }

        public void setType(Value.CString type) {
            this.type = type;
        }
    }
    public static class CBlock extends AbstractCObject{
        private List<CDeclaration.Variable> declarations;


        @Override
        String toString(int block_idx) {
            return getDeclString();
        }

        private String getDeclString(){
            return Utils.join(";\n", declarations) + (declarations.size() != 0 ? ";\n" : "\n");
        }

        @Override
        public List getChildren() {
            return null;
        }

        public List<CDeclaration.Variable> getDeclarations() {
            return declarations;
        }

        public void setDeclarations(List<CDeclaration.Variable> declarations) {
            this.declarations = declarations;
        }
    }
    public static abstract class CExpression extends AbstractCObject{
        public static final Value.CString
                            VALUE_NAME = new Value.CString("__CONSTANT__"),
                            SWITCH_NAME = new Value.CString("__SWITCH__"),
                            TERNARY_NAME = new Value.CString("__TERNARY__"),
                            ASM_NAME = new Value.CString("__ASM__");

        @Override
        public List getChildren() {
            return asList(getName(), getChildExpressions());
        }

        public abstract Value.CString getName();
        public abstract void setName(Value.CString name);
        public abstract List<CExpression> getChildExpressions();
        public abstract void setChildExpressions(List<CExpression> childExpressions);

        public static class ValueExpression extends CExpression {
            private Value value;

            public ValueExpression(Value v){
                this.value = v;
            }

            @Override
            public Value.CString getName() { return VALUE_NAME; }

            @Override
            public void setName(Value.CString name) {}

            @Override
            public List<CExpression> getChildExpressions() { return null; }

            @Override
            public void setChildExpressions(List<CExpression> childExpressions) {}

            @Override
            String toString(int block_idx) {
                return value.toString(block_idx);
            }

            public Value getValue() {
                return value;
            }

            public void setValue(Value value) {
                this.value = value;
            }
        }
        public static class SwitchExpression extends CExpression {
            private CDeclaration.Variable switch_owner;
            private List<CExpression> branches;
            private List<Value> branchConditions;

            @Override
            public Value.CString getName() { return SWITCH_NAME; }

            @Override
            public void setName(Value.CString name) {}

            @Override
            public List<CExpression> getChildExpressions() { return branches; }

            public List<Value> getBranchConditions() {
                return branchConditions;
            }

            public void setBranchConditions(List<Value> branchConditions) {
                this.branchConditions = branchConditions;
            }

            @Override
            public void setChildExpressions(List<CExpression> childExpressions) {
                this.branches = childExpressions;
            }

            public CDeclaration.Variable getSwitchOwner() {
                return switch_owner;
            }

            public void setSwitchOwner(CDeclaration.Variable switch_owner) {
                this.switch_owner = switch_owner;
            }

            @Override
            String toString(int block_idx) {
                StringBuilder sb = new StringBuilder(rep(block_idx) + "switch(" + switch_owner.name + "){\n");
                String tab = rep(block_idx + 1);
                for(int i = 0; i < branchConditions.size(); ++i){
                    sb.append(tab).append("case ").append(branchConditions.get(i)).append(":\n");
                    sb.append(branches.get(i).toString(block_idx + 1)).append("\n");
                }
                return sb.toString();
            }
        }
        public static class TernaryExpression extends CExpression {
            private CExpression condition;
            private CExpression onTrue, onFalse;

            public TernaryExpression(CExpression condition, CExpression onTrue, CExpression onFalse){
                this.condition = condition;
                this.onTrue = onTrue;
                this.onFalse = onFalse;
            }

            @Override
            public Value.CString getName() { return TERNARY_NAME; }

            @Override
            public void setName(Value.CString name) {}

            @Override
            public List<CExpression> getChildExpressions() { return asList(condition, onTrue, onFalse); }

            @Override
            public void setChildExpressions(List<CExpression> childExpressions) {
                this.condition = childExpressions.get(0);
                this.onTrue = childExpressions.get(1);
                this.onFalse = childExpressions.get(2);
            }

            public CExpression getCondition() {
                return condition;
            }

            public void setCondition(CExpression condition) {
                this.condition = condition;
            }

            public CExpression getOnTrue() {
                return onTrue;
            }

            public void setOnTrue(CExpression onTrue) {
                this.onTrue = onTrue;
            }

            public CExpression getOnFalse() {
                return onFalse;
            }

            public void setOnFalse(CExpression onFalse) {
                this.onFalse = onFalse;
            }

            @Override
            String toString(int block_idx) {
                return condition + " ? " + onTrue + " : " + onFalse;
            }
        }
        public static class ASMExpression extends CExpression {
            private Value.CString asmValue;

            public ASMExpression(Value.CString asmValue){
                this.asmValue = asmValue;
            }

            @Override
            public Value.CString getName() { return TERNARY_NAME; }

            @Override
            public void setName(Value.CString name) {}

            @Override
            public List<CExpression> getChildExpressions() { return null; }

            @Override
            public void setChildExpressions(List<CExpression> childExpressions) {}

            public Value.CString getAsmValue() {
                return asmValue;
            }

            public void setAsmValue(Value.CString asmValue) {
                this.asmValue = asmValue;
            }

            @Override
            String toString(int block_idx) {
                return "asm(" + asmValue + ")";
            }
        }
    }

    public static abstract class CDeclaration extends AbstractCObject{

        public static class Comment extends CDeclaration{
            private Value.CString value;
            private boolean IsBlock;

            public boolean isBlock() {
                return IsBlock;
            }

            public void setBlock(boolean block) {
                IsBlock = block;
            }

            public Value.CString getValue() {
                return value;
            }

            public void setValue(Value.CString value) {
                this.value = value;
            }

            @Override
            String toString(int block_idx) {
                return rep(block_idx) + value;
            }

            @Override
            public List getChildren() {
                return asList(value);
            }
        }
        public static class Macro extends CDeclaration{
            private List<Value.CString> parameters;
            private Value.CString name;

            public List<Value.CString> getParameters() {
                return parameters;
            }

            public void setParameters(List<Value.CString> parameters) {
                this.parameters = parameters;
            }

            public Value.CString getName() {
                return name;
            }

            public void setName(Value.CString name) {
                this.name = name;
            }

            @Override
            String toString(int block_idx) {
                return name + Utils.join(" ", parameters);
            }

            @Override
            public List getChildren() {
                return asList(parameters, name);
            }
        }
        public static class Variable extends CDeclaration{
            private Value.CString name;
            private CType type;

            public Value.CString getName() {
                return name;
            }

            public void setName(Value.CString name) {
                this.name = name;
            }

            public CType getType() {
                return type;
            }

            public void setType(CType type) {
                this.type = type;
            }

            @Override
            String toString(int block_idx) {
                return Utils.join(" ", type, name);
            }

            @Override
            public List getChildren() {
                return asList(type);
            }
        }
        public static class Function extends CDeclaration{
            private Value.CString name;
            private Value.CString[] attributes;
            private CType return_type;
            private Variable[] arguments;

            public Value.CString getName() {
                return name;
            }

            public void setName(Value.CString name) {
                this.name = name;
            }

            public Value.CString[] getAttributes() {
                return attributes;
            }

            public void setAttributes(Value.CString[] attributes) {
                this.attributes = attributes;
            }

            public CType getReturn_type() {
                return return_type;
            }

            public void setReturn_type(CType return_type) {
                this.return_type = return_type;
            }

            public Variable[] getArguments() {
                return arguments;
            }

            public void setArguments(Variable[] arguments) {
                this.arguments = arguments;
            }

            @Override
            String toString(int block_idx) {
                return Utils.join(" ", attributes, return_type, name) + "(" + Utils.join(",", arguments) + ")";
            }

            @Override
            public List getChildren() {
                return asList(name, attributes, return_type, arguments);
            }
        }
    }

    public static class CDeclarationList extends AbstractCObject{
        public List<CDeclaration> declarations;

        @Override
        public String toString(int block_idx) {
            return join(block_idx, declarations);
        }

        @Override
        public List getChildren() {
            return declarations;
        }
    }


    public static <T> T[] ArrayOf(ParserRuleContext ctx, CVisitor v, IntFunction<T[]> generator) {
        return Stream.of(ctx).map((Function<ParserRuleContext, Object>) v::visit).toArray(generator);
    }

}
