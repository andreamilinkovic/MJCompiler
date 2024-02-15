// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class ForeachStmt extends Statement {

    private Designator Designator;
    private ForeachName ForeachName;
    private Statement Statement;
    private ForeachEnd ForeachEnd;

    public ForeachStmt (Designator Designator, ForeachName ForeachName, Statement Statement, ForeachEnd ForeachEnd) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ForeachName=ForeachName;
        if(ForeachName!=null) ForeachName.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ForeachEnd=ForeachEnd;
        if(ForeachEnd!=null) ForeachEnd.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ForeachName getForeachName() {
        return ForeachName;
    }

    public void setForeachName(ForeachName ForeachName) {
        this.ForeachName=ForeachName;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ForeachEnd getForeachEnd() {
        return ForeachEnd;
    }

    public void setForeachEnd(ForeachEnd ForeachEnd) {
        this.ForeachEnd=ForeachEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ForeachName!=null) ForeachName.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ForeachEnd!=null) ForeachEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ForeachName!=null) ForeachName.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ForeachEnd!=null) ForeachEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ForeachName!=null) ForeachName.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ForeachEnd!=null) ForeachEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForeachStmt(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForeachName!=null)
            buffer.append(ForeachName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForeachEnd!=null)
            buffer.append(ForeachEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForeachStmt]");
        return buffer.toString();
    }
}
