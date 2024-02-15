// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class BlockStmt extends Statement {

    private OptStmtList OptStmtList;

    public BlockStmt (OptStmtList OptStmtList) {
        this.OptStmtList=OptStmtList;
        if(OptStmtList!=null) OptStmtList.setParent(this);
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
        if(OptStmtList!=null) OptStmtList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptStmtList!=null) OptStmtList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptStmtList!=null) OptStmtList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BlockStmt(\n");

        if(OptStmtList!=null)
            buffer.append(OptStmtList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BlockStmt]");
        return buffer.toString();
    }
}
