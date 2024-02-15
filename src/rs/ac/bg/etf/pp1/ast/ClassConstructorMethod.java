// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class ClassConstructorMethod extends ClassConstrMeth {

    private ConstrMethList ConstrMethList;

    public ClassConstructorMethod (ConstrMethList ConstrMethList) {
        this.ConstrMethList=ConstrMethList;
        if(ConstrMethList!=null) ConstrMethList.setParent(this);
    }

    public ConstrMethList getConstrMethList() {
        return ConstrMethList;
    }

    public void setConstrMethList(ConstrMethList ConstrMethList) {
        this.ConstrMethList=ConstrMethList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstrMethList!=null) ConstrMethList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstrMethList!=null) ConstrMethList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstrMethList!=null) ConstrMethList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassConstructorMethod(\n");

        if(ConstrMethList!=null)
            buffer.append(ConstrMethList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassConstructorMethod]");
        return buffer.toString();
    }
}
