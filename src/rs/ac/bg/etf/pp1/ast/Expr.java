// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class Expr implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private ExprTerm ExprTerm;
    private OptAddTerms OptAddTerms;

    public Expr (ExprTerm ExprTerm, OptAddTerms OptAddTerms) {
        this.ExprTerm=ExprTerm;
        if(ExprTerm!=null) ExprTerm.setParent(this);
        this.OptAddTerms=OptAddTerms;
        if(OptAddTerms!=null) OptAddTerms.setParent(this);
    }

    public ExprTerm getExprTerm() {
        return ExprTerm;
    }

    public void setExprTerm(ExprTerm ExprTerm) {
        this.ExprTerm=ExprTerm;
    }

    public OptAddTerms getOptAddTerms() {
        return OptAddTerms;
    }

    public void setOptAddTerms(OptAddTerms OptAddTerms) {
        this.OptAddTerms=OptAddTerms;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprTerm!=null) ExprTerm.accept(visitor);
        if(OptAddTerms!=null) OptAddTerms.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprTerm!=null) ExprTerm.traverseTopDown(visitor);
        if(OptAddTerms!=null) OptAddTerms.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprTerm!=null) ExprTerm.traverseBottomUp(visitor);
        if(OptAddTerms!=null) OptAddTerms.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr(\n");

        if(ExprTerm!=null)
            buffer.append(ExprTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptAddTerms!=null)
            buffer.append(OptAddTerms.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expr]");
        return buffer.toString();
    }
}
