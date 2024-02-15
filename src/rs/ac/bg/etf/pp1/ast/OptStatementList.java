// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class OptStatementList extends OptStmtList {

    private OptStmtList OptStmtList;
    private Statement Statement;

    public OptStatementList (OptStmtList OptStmtList, Statement Statement) {
        this.OptStmtList=OptStmtList;
        if(OptStmtList!=null) OptStmtList.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public OptStmtList getOptStmtList() {
        return OptStmtList;
    }

    public void setOptStmtList(OptStmtList OptStmtList) {
        this.OptStmtList=OptStmtList;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptStmtList!=null) OptStmtList.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptStmtList!=null) OptStmtList.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptStmtList!=null) OptStmtList.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptStatementList(\n");

        if(OptStmtList!=null)
            buffer.append(OptStmtList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptStatementList]");
        return buffer.toString();
    }
}
