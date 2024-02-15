// generated with ast extension for cup
// version 0.8
// 26/5/2023 14:14:18


package rs.ac.bg.etf.pp1.ast;

public class SingleFormPar extends FormPars {

    private FormParsItem FormParsItem;

    public SingleFormPar (FormParsItem FormParsItem) {
        this.FormParsItem=FormParsItem;
        if(FormParsItem!=null) FormParsItem.setParent(this);
    }

    public FormParsItem getFormParsItem() {
        return FormParsItem;
    }

    public void setFormParsItem(FormParsItem FormParsItem) {
        this.FormParsItem=FormParsItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParsItem!=null) FormParsItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsItem!=null) FormParsItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsItem!=null) FormParsItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleFormPar(\n");

        if(FormParsItem!=null)
            buffer.append(FormParsItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleFormPar]");
        return buffer.toString();
    }
}
