package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FormParsItem;
import rs.ac.bg.etf.pp1.ast.VariableItem;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;

	public int getCount() {
		return count;
	}

	public static class FormParamCounter extends CounterVisitor {

		public void visit(FormParsItem formParamDecl) {
			count++;
		}
	}

	public static class VarCounter extends CounterVisitor {

		public void visit(VariableItem varDecl) {
			count++;
		}
	}
}
