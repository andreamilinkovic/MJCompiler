// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class Constructors extends ConstrMethList {

    private ConstructorDecl ConstructorDecl;
    private ConstrMethList ConstrMethList;

    public Constructors (ConstructorDecl ConstructorDecl, ConstrMethList ConstrMethList) {
        this.ConstructorDecl=ConstructorDecl;
        if(ConstructorDecl!=null) ConstructorDecl.setParent(this);
        this.ConstrMethList=ConstrMethList;
        if(ConstrMethList!=null) ConstrMethList.setParent(this);
    }

    public ConstructorDecl getConstructorDecl() {
        return ConstructorDecl;
    }

    public void setConstructorDecl(ConstructorDecl ConstructorDecl) {
        this.ConstructorDecl=ConstructorDecl;
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
        if(ConstructorDecl!=null) ConstructorDecl.accept(visitor);
        if(ConstrMethList!=null) ConstrMethList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorDecl!=null) ConstructorDecl.traverseTopDown(visitor);
        if(ConstrMethList!=null) ConstrMethList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorDecl!=null) ConstructorDecl.traverseBottomUp(visitor);
        if(ConstrMethList!=null) ConstrMethList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Constructors(\n");

        if(ConstructorDecl!=null)
            buffer.append(ConstructorDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstrMethList!=null)
            buffer.append(ConstrMethList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Constructors]");
        return buffer.toString();
    }
}
