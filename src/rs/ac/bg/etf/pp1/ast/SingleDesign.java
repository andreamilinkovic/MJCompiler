// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class SingleDesign extends OptDesignatorList {

    private OptDesignator OptDesignator;

    public SingleDesign (OptDesignator OptDesignator) {
        this.OptDesignator=OptDesignator;
        if(OptDesignator!=null) OptDesignator.setParent(this);
    }

    public OptDesignator getOptDesignator() {
        return OptDesignator;
    }

    public void setOptDesignator(OptDesignator OptDesignator) {
        this.OptDesignator=OptDesignator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptDesignator!=null) OptDesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptDesignator!=null) OptDesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptDesignator!=null) OptDesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleDesign(\n");

        if(OptDesignator!=null)
            buffer.append(OptDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleDesign]");
        return buffer.toString();
    }
}
