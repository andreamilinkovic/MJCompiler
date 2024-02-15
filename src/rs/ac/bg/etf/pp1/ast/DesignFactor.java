// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class DesignFactor extends Factor {

    private Designator Designator;
    private OptMethCall OptMethCall;

    public DesignFactor (Designator Designator, OptMethCall OptMethCall) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.OptMethCall=OptMethCall;
        if(OptMethCall!=null) OptMethCall.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
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
        if(Designator!=null) Designator.accept(visitor);
        if(OptMethCall!=null) OptMethCall.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(OptMethCall!=null) OptMethCall.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(OptMethCall!=null) OptMethCall.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignFactor(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptMethCall!=null)
            buffer.append(OptMethCall.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignFactor]");
        return buffer.toString();
    }
}
