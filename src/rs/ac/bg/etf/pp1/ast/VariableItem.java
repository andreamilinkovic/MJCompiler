// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class VariableItem extends VarItem {

    private String varName;
    private ArrayType ArrayType;

    public VariableItem (String varName, ArrayType ArrayType) {
        this.varName=varName;
        this.ArrayType=ArrayType;
        if(ArrayType!=null) ArrayType.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public ArrayType getArrayType() {
        return ArrayType;
    }

    public void setArrayType(ArrayType ArrayType) {
        this.ArrayType=ArrayType;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayType!=null) ArrayType.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayType!=null) ArrayType.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayType!=null) ArrayType.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableItem(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(ArrayType!=null)
            buffer.append(ArrayType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableItem]");
        return buffer.toString();
    }
}
