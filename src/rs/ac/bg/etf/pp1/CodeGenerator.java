package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}
	
	protected static Obj currDesign = null;
		
	/**************** Method ****************/
	public void visit(MethDeclTypeName methodTypeName) {

		if ("main".equalsIgnoreCase(methodTypeName.getMethodName())) {
			mainPc = Code.pc;
		}

		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	}

	public void visit(MethodDeclaration methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	/***************** Return ***************/
	public void visit(ReturnStmt returnStmt) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	/***************** Print ****************/
	public void visit(PrintStmt printStmt) {
		if (printStmt.getExpr().struct == Tab.charType) {
			Code.loadConst(printStmt.getOptNumConst().obj.getAdr());
			Code.put(Code.bprint);
		} else {
			Code.loadConst(printStmt.getOptNumConst().obj.getAdr());
			Code.put(Code.print);
		}
	}

	public void visit(OptPrint print) {
		print.obj = new Obj(Obj.Con, "width", Tab.intType, print.getN1(), 0);
	}

	public void visit(NoOptPrint print) {
		print.obj = new Obj(Obj.Con, "width", Tab.intType, 3, 0);
	}
	
	/***************** Read ******************/	
	public void visit(ReadStmt readStmt) {
		Obj node = readStmt.getDesignator().obj;
		if (node.getType() == Tab.charType) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read); 
		}
		Code.store(node);
	}
	
	/************ AssignStatement ************/
	public void visit(DesignAssignOp assignOper) {
		Code.store(assignOper.getDesignator().obj);
	}

	/****************** Inc *****************/
	public void visit(IncEff inc) {
		if (inc.getDesignator() instanceof DesignVar) {
			Code.load(inc.getDesignator().obj);
			Code.load(new Obj(Obj.Con, "", Tab.intType, 1, 0));
			Code.put(Code.add);
			Code.store(inc.getDesignator().obj);
		}
	}
	
	/****************** Dec *****************/
	public void visit(DecEff dec) {
		if (dec.getDesignator() instanceof DesignVar) {
			Code.load(dec.getDesignator().obj);
			Code.load(new Obj(Obj.Con, "", Tab.intType, 1, 0));
			Code.put(Code.sub);
			Code.store(dec.getDesignator().obj);
		}
	}
	
	/**************** ProcCall ***************/
	public void visit(ProcCall func) {
		int offset = func.getDesignator().obj.getAdr() - Code.pc;
		/*if(func.getOptActPars() instanceof OptionalActPars) {
		}*/
		
		Code.put(Code.call);
		Code.put2(offset);
		
		if(func.getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	/**************** Designator ****************/
	public void visit(DesignVar design) {
		currDesign = Tab.find(design.obj.getName());
		SyntaxNode parent = design.getParent();

		if(parent.getClass() == DesignArray.class) {
			Code.load(design.obj);
		}
	}	
	
	public void visit(DesignArray design) {
		if(design.getDesignator() instanceof DesignArray) {
			Code.load(new Obj(Obj.Con, "$", Tab.intType, currDesign.getFpPos(), 0));
			Code.put(Code.mul);
			Code.put(Code.add);
		}		
	}
	
	/**************** Factor ****************/
	public void visit(FactorNum factor) {
		Obj con = new Obj(Obj.Con, "$", factor.struct, factor.getN1(), 0);
		Code.load(con); // stvlja const na ExprStack
		
		if(factor.getParent().getParent().getParent().getParent().getClass() == FactorNewMatrix.class) {
			currDesign.setFpPos(factor.getN1());
		}
	}

	public void visit(FactorChar factor) {
		Obj con = new Obj(Obj.Con, "$", factor.struct, factor.getC1(), 0);
		Code.load(con); // stvlja const na ExprStack
	}

	public void visit(FactorBool factor) {
		Obj con = new Obj(Obj.Con, "$", factor.struct);
		con.setAdr("true".equals(factor.getB1().toString()) ? 1 : 0);
		con.setLevel(0);

		Code.load(con); // stvlja const na ExprStack
	}

	public void visit(DesignFactor factor) {
		if (factor.getOptMethCall() instanceof OptionalMethCall) {
			int offset = factor.getDesignator().obj.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
		} else if (factor.getOptMethCall() instanceof NoOptMethCall) {
			Obj node = new Obj(factor.getDesignator().obj.getKind(), "", factor.getDesignator().obj.getType());
			node.setAdr(factor.getDesignator().obj.getAdr());
			node.setLevel(factor.getDesignator().obj.getLevel());
			Code.load(node);
		}
	}
	
	public void visit(FactorNewArray array) {
		Code.put(Code.newarray);
		
		if(array.getType().struct == Tab.intType) {
			Code.put(1);
		} else {
			Code.put(0);
		}
	}
	
	public void visit(FactorNewMatrix array) {
		Code.put(Code.mul);
		Code.put(Code.newarray);
		
		if(array.getType().struct == Tab.intType) {
			Code.put(1);
		} else {
			Code.put(0);
		}
	}

	/****************** Expr ******************/
	public void visit(ExpressionTerm expr) {
		if (expr.getOtpMinusExpr() instanceof NegativeExpr)
			Code.put(Code.neg);
	}

	public void visit(OptionalAddTerms oper) {
		if (oper.getAddop() instanceof PlusOperation) {
			Code.put(Code.add);
		} else if (oper.getAddop() instanceof MinusOperation) {
			Code.put(Code.sub);
		}
	}

	public void visit(OptionalMulFacts oper) {
		if (oper.getMulop() instanceof MulOperation) {
			Code.put(Code.mul);
		} else if (oper.getMulop() instanceof DivOperation) {
			Code.put(Code.div);
		} else if (oper.getMulop() instanceof ModOperation) {
			Code.put(Code.rem);
		}
	}
	
	/*************** Conditions ***************/
	public void visit(MultipleCondTerms cond) {
		
	}
	
	public void visit(MultipleCondFact cond) {
		if(cond.getRelop() instanceof EqualsOperation){
			Code.put(Code.eq);
		} else if (cond.getRelop() instanceof NotEqualsOperation) {
			Code.put(Code.ne);
		} else if (cond.getRelop() instanceof GreaterOperation) {
			Code.put(Code.gt);
		} else if (cond.getRelop() instanceof GreaterEqualsOperation) {
			Code.put(Code.ge);
		} else if (cond.getRelop() instanceof LessOperation) {
			Code.put(Code.lt);
		} else if (cond.getRelop() instanceof LessEqualsOperation) {
			Code.put(Code.le);
		}
	}
	
	/*************** If Statement ***************/
	public void visit(IfStmt stmt) {
		
	}
}
