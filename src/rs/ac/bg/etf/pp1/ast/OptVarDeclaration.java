// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class OptVarDeclaration extends OptVarDecl {

    private OptVarDecl OptVarDecl;
    private VarDecl VarDecl;

    public OptVarDeclaration (OptVarDecl OptVarDecl, VarDecl VarDecl) {
        this.OptVarDecl=OptVarDecl;
        if(OptVarDecl!=null) OptVarDecl.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public OptVarDecl getOptVarDecl() {
        return OptVarDecl;
    }

    public void setOptVarDecl(OptVarDecl OptVarDecl) {
        this.OptVarDecl=OptVarDecl;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptVarDecl!=null) OptVarDecl.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptVarDecl!=null) OptVarDecl.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptVarDecl!=null) OptVarDecl.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptVarDeclaration(\n");

        if(OptVarDecl!=null)
            buffer.append(OptVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptVarDeclaration]");
        return buffer.toString();
    }
}
