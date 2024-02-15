// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class FactorNewMeth extends Factor {

    private Type Type;
    private OptMethCall OptMethCall;

    public FactorNewMeth (Type Type, OptMethCall OptMethCall) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.OptMethCall=OptMethCall;
        if(OptMethCall!=null) OptMethCall.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public OptMethCall getOptMethCall() {
        return OptMethCall;
    }

    public void setOptMethCall(OptMethCall OptMethCall) {
        this.OptMethCall=OptMethCall;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OptMethCall!=null) OptMethCall.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OptMethCall!=null) OptMethCall.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OptMethCall!=null) OptMethCall.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorNewMeth(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptMethCall!=null)
            buffer.append(OptMethCall.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorNewMeth]");
        return buffer.toString();
    }
}
