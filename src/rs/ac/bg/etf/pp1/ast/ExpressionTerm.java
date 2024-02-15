// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class ExpressionTerm extends ExprTerm {

    private OtpMinusExpr OtpMinusExpr;
    private Term Term;

    public ExpressionTerm (OtpMinusExpr OtpMinusExpr, Term Term) {
        this.OtpMinusExpr=OtpMinusExpr;
        if(OtpMinusExpr!=null) OtpMinusExpr.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
    }

    public OtpMinusExpr getOtpMinusExpr() {
        return OtpMinusExpr;
    }

    public void setOtpMinusExpr(OtpMinusExpr OtpMinusExpr) {
        this.OtpMinusExpr=OtpMinusExpr;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OtpMinusExpr!=null) OtpMinusExpr.accept(visitor);
        if(Term!=null) Term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OtpMinusExpr!=null) OtpMinusExpr.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OtpMinusExpr!=null) OtpMinusExpr.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExpressionTerm(\n");

        if(OtpMinusExpr!=null)
            buffer.append(OtpMinusExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExpressionTerm]");
        return buffer.toString();
    }
}
