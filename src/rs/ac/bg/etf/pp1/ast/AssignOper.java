// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class AssignOper extends DesignatorStatement {

    private DesignatorAssignOperation DesignatorAssignOperation;

    public AssignOper (DesignatorAssignOperation DesignatorAssignOperation) {
        this.DesignatorAssignOperation=DesignatorAssignOperation;
        if(DesignatorAssignOperation!=null) DesignatorAssignOperation.setParent(this);
    }

    public DesignatorAssignOperation getDesignatorAssignOperation() {
        return DesignatorAssignOperation;
    }

    public void setDesignatorAssignOperation(DesignatorAssignOperation DesignatorAssignOperation) {
        this.DesignatorAssignOperation=DesignatorAssignOperation;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorAssignOperation!=null) DesignatorAssignOperation.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorAssignOperation!=null) DesignatorAssignOperation.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorAssignOperation!=null) DesignatorAssignOperation.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AssignOper(\n");

        if(DesignatorAssignOperation!=null)
            buffer.append(DesignatorAssignOperation.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AssignOper]");
        return buffer.toString();
    }
}
