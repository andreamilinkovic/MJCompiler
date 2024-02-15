// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclaration extends ClassDecl {

    private String I1;
    private ExtendsClass ExtendsClass;
    private OptVarDecl OptVarDecl;
    private ClassConstrMeth ClassConstrMeth;

    public ClassDeclaration (String I1, ExtendsClass ExtendsClass, OptVarDecl OptVarDecl, ClassConstrMeth ClassConstrMeth) {
        this.I1=I1;
        this.ExtendsClass=ExtendsClass;
        if(ExtendsClass!=null) ExtendsClass.setParent(this);
        this.OptVarDecl=OptVarDecl;
        if(OptVarDecl!=null) OptVarDecl.setParent(this);
        this.ClassConstrMeth=ClassConstrMeth;
        if(ClassConstrMeth!=null) ClassConstrMeth.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ExtendsClass getExtendsClass() {
        return ExtendsClass;
    }

    public void setExtendsClass(ExtendsClass ExtendsClass) {
        this.ExtendsClass=ExtendsClass;
    }

    public OptVarDecl getOptVarDecl() {
        return OptVarDecl;
    }

    public void setOptVarDecl(OptVarDecl OptVarDecl) {
        this.OptVarDecl=OptVarDecl;
    }

    public ClassConstrMeth getClassConstrMeth() {
        return ClassConstrMeth;
    }

    public void setClassConstrMeth(ClassConstrMeth ClassConstrMeth) {
        this.ClassConstrMeth=ClassConstrMeth;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtendsClass!=null) ExtendsClass.accept(visitor);
        if(OptVarDecl!=null) OptVarDecl.accept(visitor);
        if(ClassConstrMeth!=null) ClassConstrMeth.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendsClass!=null) ExtendsClass.traverseTopDown(visitor);
        if(OptVarDecl!=null) OptVarDecl.traverseTopDown(visitor);
        if(ClassConstrMeth!=null) ClassConstrMeth.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendsClass!=null) ExtendsClass.traverseBottomUp(visitor);
        if(OptVarDecl!=null) OptVarDecl.traverseBottomUp(visitor);
        if(ClassConstrMeth!=null) ClassConstrMeth.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclaration(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ExtendsClass!=null)
            buffer.append(ExtendsClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptVarDecl!=null)
            buffer.append(OptVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassConstrMeth!=null)
            buffer.append(ClassConstrMeth.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclaration]");
        return buffer.toString();
    }
}
