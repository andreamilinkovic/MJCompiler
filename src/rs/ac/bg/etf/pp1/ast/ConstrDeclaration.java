// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class ConstrDeclaration extends ConstructorDecl {

    private String I1;
    private OptFormPars OptFormPars;
    private OptVarDecl OptVarDecl;
    private OptStmtList OptStmtList;

    public ConstrDeclaration (String I1, OptFormPars OptFormPars, OptVarDecl OptVarDecl, OptStmtList OptStmtList) {
        this.I1=I1;
        this.OptFormPars=OptFormPars;
        if(OptFormPars!=null) OptFormPars.setParent(this);
        this.OptVarDecl=OptVarDecl;
        if(OptVarDecl!=null) OptVarDecl.setParent(this);
        this.OptStmtList=OptStmtList;
        if(OptStmtList!=null) OptStmtList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public OptFormPars getOptFormPars() {
        return OptFormPars;
    }

    public void setOptFormPars(OptFormPars OptFormPars) {
        this.OptFormPars=OptFormPars;
    }

    public OptVarDecl getOptVarDecl() {
        return OptVarDecl;
    }

    public void setOptVarDecl(OptVarDecl OptVarDecl) {
        this.OptVarDecl=OptVarDecl;
    }

    public OptStmtList getOptStmtList() {
        return OptStmtList;
    }

    public void setOptStmtList(OptStmtList OptStmtList) {
        this.OptStmtList=OptStmtList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptFormPars!=null) OptFormPars.accept(visitor);
        if(OptVarDecl!=null) OptVarDecl.accept(visitor);
        if(OptStmtList!=null) OptStmtList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptFormPars!=null) OptFormPars.traverseTopDown(visitor);
        if(OptVarDecl!=null) OptVarDecl.traverseTopDown(visitor);
        if(OptStmtList!=null) OptStmtList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptFormPars!=null) OptFormPars.traverseBottomUp(visitor);
        if(OptVarDecl!=null) OptVarDecl.traverseBottomUp(visitor);
        if(OptStmtList!=null) OptStmtList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstrDeclaration(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(OptFormPars!=null)
            buffer.append(OptFormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptVarDecl!=null)
            buffer.append(OptVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptStmtList!=null)
            buffer.append(OptStmtList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstrDeclaration]");
        return buffer.toString();
    }
}
