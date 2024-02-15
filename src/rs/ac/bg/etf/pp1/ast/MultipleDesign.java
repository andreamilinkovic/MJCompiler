// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class MultipleDesign extends OptDesignatorList {

    private OptDesignatorList OptDesignatorList;
    private OptDesignator OptDesignator;

    public MultipleDesign (OptDesignatorList OptDesignatorList, OptDesignator OptDesignator) {
        this.OptDesignatorList=OptDesignatorList;
        if(OptDesignatorList!=null) OptDesignatorList.setParent(this);
        this.OptDesignator=OptDesignator;
        if(OptDesignator!=null) OptDesignator.setParent(this);
    }

    public OptDesignatorList getOptDesignatorList() {
        return OptDesignatorList;
    }

    public void setOptDesignatorList(OptDesignatorList OptDesignatorList) {
        this.OptDesignatorList=OptDesignatorList;
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
        if(OptDesignatorList!=null) OptDesignatorList.accept(visitor);
        if(OptDesignator!=null) OptDesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptDesignatorList!=null) OptDesignatorList.traverseTopDown(visitor);
        if(OptDesignator!=null) OptDesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptDesignatorList!=null) OptDesignatorList.traverseBottomUp(visitor);
        if(OptDesignator!=null) OptDesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleDesign(\n");

        if(OptDesignatorList!=null)
            buffer.append(OptDesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptDesignator!=null)
            buffer.append(OptDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleDesign]");
        return buffer.toString();
    }
}
