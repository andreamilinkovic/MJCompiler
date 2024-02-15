// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class MultipleAssign extends DesignatorStatement {

    private OptDesignatorList OptDesignatorList;
    private Designator Designator;

    public MultipleAssign (OptDesignatorList OptDesignatorList, Designator Designator) {
        this.OptDesignatorList=OptDesignatorList;
        if(OptDesignatorList!=null) OptDesignatorList.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
    }

    public OptDesignatorList getOptDesignatorList() {
        return OptDesignatorList;
    }

    public void setOptDesignatorList(OptDesignatorList OptDesignatorList) {
        this.OptDesignatorList=OptDesignatorList;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptDesignatorList!=null) OptDesignatorList.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptDesignatorList!=null) OptDesignatorList.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptDesignatorList!=null) OptDesignatorList.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleAssign(\n");

        if(OptDesignatorList!=null)
            buffer.append(OptDesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleAssign]");
        return buffer.toString();
    }
}
