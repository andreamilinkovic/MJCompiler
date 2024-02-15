package rs.ac.bg.etf.pp1;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {

	protected Struct currentType = Tab.noType;
	protected int currentLevel = 0;
	protected boolean isClassDeclaration = false;
	protected Obj currentMethod = null;
	protected Obj currentCallFunc = null;
	
	int nVars;

	boolean returnFound = false;
	boolean mainFound = false;
	boolean errorDetected = false;
	boolean inWhile = false, inForeach = false;

	protected ArrayList<Obj> multipleAssignList = new ArrayList<Obj>();

	protected ArrayList<String> funcWithPars = new ArrayList<String>();
	protected ArrayList<ArrayList<Obj>> formalPars = new ArrayList<ArrayList<Obj>>();

	protected ArrayList<Struct> actualPars = new ArrayList<Struct>();

	Logger log = Logger.getLogger(getClass());

	protected static Struct boolType = null;

	protected void init() {
		// inicijalizacija tabele
		if (boolType == null) {
			boolType = new Struct(Struct.Bool);
			// ubacuje bool u tabelu simbola
			Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
		}
	}

	/************ Metode za ispis ************/
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0) {
			msg.insert(0, "Greska na " + line + ": ");
		}
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	/*************** Program ***************/
	@Override
	public void visit(ProgName pName) {
		// report_info("INFO: Pocetak programa " + pName.getPName(), pName);

		init();

		pName.obj = Tab.insert(Obj.Prog, pName.getPName(), Tab.noType);
		Tab.openScope();

		pName.obj.setLevel(currentLevel);
		// currentLevel++;
	}

	@Override
	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();

		// nije pronadjen main
		if (!mainFound)
			report_error("metoda main nije deklarisana", null);

		// currentLevel--;
	}

	/***************** Type *****************/
	@Override
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());

		if (typeNode == Tab.noObj) {
			report_error("nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", type);
			type.struct = Tab.noType;
			currentType = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
				currentType = typeNode.getType();
			} else {
				report_error("ime " + type.getTypeName() + " ne predstavlja tip", type);
				type.struct = Tab.noType;
				currentType = Tab.noType;
			}
		}
	}

	@Override
	public void visit(MethodReturnTypeVoid mrtv) {
		currentType = Tab.noType;
	}

	/***************** Const ****************/
	@Override
	public void visit(ConstItem constItem) {
		Obj constNode = Tab.find(constItem.getId());

		if (constNode == Tab.noObj) {
			// konstanta nije u tabeli
			if (currentType.isRefType()) {
				// konstanta ne moze biti referenca
				report_error("konstanta ne moze biti referenca po tipu", constItem);
			} else {
				if (currentType.assignableTo(constItem.getConstType().obj.getType())) {
					// odgovarajuceg tipa
					constNode = Tab.insert(Obj.Con, constItem.getId(), currentType);
					constNode.setAdr(constItem.getConstType().obj.getAdr());
					// report_info("INFO: Deklarisana konstanta " + constItem.getId(), constItem);
				} else {
					// neodgovarajuceg tipa
					report_error("navedeni i stvarni tip konstante " + constItem.getId() + " se ne poklapaju",
							constItem);
				}
			}
		} else {
			// vec je deklarisana konstanta
			report_error("konstanta " + constItem.getId() + " je vec deklarisana", constItem);
		}

		constNode.setLevel(currentLevel);
	}

	@Override
	public void visit(ConstNum constNum) {
		constNum.obj = new Obj(Obj.Con, "", Tab.intType);
		constNum.obj.setAdr(constNum.getValue());
	}

	@Override
	public void visit(ConstChar constChar) {
		constChar.obj = new Obj(Obj.Con, "", Tab.charType);
		constChar.obj.setAdr(constChar.getValue());
	}

	@Override
	public void visit(ConstBool constBool) {
		constBool.obj = new Obj(Obj.Con, "", boolType);
		constBool.obj.setAdr(constBool.getValue().toString().equals("true") ? 1 : 0);
	}

	/******************* Var ******************/
	@Override
	public void visit(VariableItem varItem) {
		Obj varNode = Tab.find(varItem.getVarName());
		int kind = isClassDeclaration ? Obj.Fld : Obj.Var;

		if (varNode == Tab.noObj && currentType != Tab.noType) {
			if (varItem.getArrayType() instanceof ArrayVar || varItem.getArrayType() instanceof ArrayVarTwoDim) {
				// deklaracija niza
				varNode = Tab.insert(kind, varItem.getVarName(), new Struct(Struct.Array, currentType));
				// report_info("INFO: Deklarisan niz " + varItem.getVarName(), varItem);
			} else {
				// deklaracija promenljive
				varNode = Tab.insert(kind, varItem.getVarName(), currentType);
				/*
				 * report_info("INFO: Deklarisana " + (currentLevel == 0 ? "globalna " : "") +
				 * "promenljiva " + varItem.getVarName(), varItem);
				 */
			}
		} else {
			// u trenutnom Scopu?
			if (Tab.currentScope.findSymbol(varItem.getVarName()) == null && currentType != Tab.noType) {
				if (varItem.getArrayType() instanceof ArrayVar || varItem.getArrayType() instanceof ArrayVarTwoDim) {
					// deklaracija niza
					varNode = Tab.insert(kind, varItem.getVarName(), new Struct(Struct.Array, currentType));
					// report_info("INFO: Deklarisan niz " + varItem.getVarName(), varItem);
				}  else {
					// deklaracija promenljive
					varNode = Tab.insert(kind, varItem.getVarName(), currentType);
					/*
					 * report_info("INFO: Deklarisana " + (currentLevel == 0 ? "globalna " : "") +
					 * "promenljiva " + varItem.getVarName(), varItem);
					 */
				}
			} else {
				// promenljiva je vec deklarisana
				if (currentType != Tab.noType)
					report_error("promenljiva " + varItem.getVarName() + " je vec deklarisana", varItem);
			}
		}
	}

	/***************** Method ****************/
	@Override
	public void visit(MethDeclTypeName methDeclTypeName) {
		if (Tab.find(methDeclTypeName.getMethodName()) != Tab.noObj) {
			// metoda je vec deklarisana
			report_error("metoda " + methDeclTypeName.getMethodName() + " je vec deklarisana", methDeclTypeName);
			return;
		}

		currentMethod = Tab.insert(Obj.Meth, methDeclTypeName.getMethodName(), currentType);
		methDeclTypeName.obj = currentMethod;
		methDeclTypeName.obj.setLevel(0);

		// da li ima povratnu vrednost
		if (methDeclTypeName.getMethodReturnType() instanceof MethodReturnTypeVoid) {
			// void
			returnFound = true;
		} else {
			// type
			returnFound = false;
		}

		Tab.openScope();
		currentLevel++;

		// report_info("INFO: Obradjivanje funkcije " + currentMethod.getName(), null);
	}

	@Override
	public void visit(MethodDeclaration method) {
		
		if (!returnFound && currentMethod.getType() != Tab.noType)
			report_error("metoda " + currentMethod.getName() + " nema povratnu vrednost (return)", method);

		Tab.chainLocalSymbols(currentMethod);

		/*
		 * report_info( "INFO: Deklarisana " + (currentLevel == 0 ? "globalna " : "") +
		 * "funkcija " + currentMethod.getName(), method);
		 */

		// da li je deklarisana main metoda
		if (currentMethod.getName().equals("main")) {
			mainFound = true;
			if (currentMethod.getType() != Tab.noType)
				report_error("metoda main mora biti tipa void", method);
			if(method.getOptFormPars() instanceof OptionalFormPars)
				report_error("funkcija main ne sme imati argumente", method);
		}

		Tab.closeScope();
		returnFound = false;
		/* if(!currentMethod.getName().equals("main")) */ currentMethod = null;
		currentLevel--;
	}

	@Override
	public void visit(ReturnStmt returnStmt) {
		if (currentMethod == null) {
			report_error("return naredba ne moze postojati van tela funkcije", returnStmt);
			return;
		}

		if (returnStmt.getOptRetExpr() instanceof RetExpr) {
			if (!currentMethod.getType().compatibleWith(returnStmt.getOptRetExpr().struct)
					|| currentMethod.getType() == Tab.noType) {
				report_error("funkcija " + currentMethod.getName() + " i njena povratna vrednost nisu kompatibilni",
						returnStmt);
				return;
			}
		} else {
			if (currentMethod.getType() != Tab.noType) {
				report_error("funkcija " + currentMethod.getName() + " mora biti tipa void", returnStmt);
				// return;
			}
		}

		returnFound = true;
	}

	@Override
	public void visit(RetExpr returnExpr) {
		returnExpr.struct = returnExpr.getExpr().struct;
	}

	@Override
	public void visit(NoRetExpr ret) {
		ret.struct = Tab.noType;
	}

	/**** FormPars ****/
	@Override
	public void visit(FormParsItem formPars) {
		
		Obj formParsNode = Tab.currentScope().findSymbol(formPars.getId());
		int kind = isClassDeclaration ? Obj.Fld : Obj.Var;

		// promenljiva vec deklarisana
		if (formParsNode != null) {
			report_error("formalni parametar " + formPars.getId() + " je vec deklarisan", formPars);
			return;
		}

		// dodaj u symbolTable
		if (formPars.getArrayType() instanceof ArrayVar || formPars.getArrayType() instanceof ArrayVarTwoDim) {
			// deklaracija niza
			formParsNode = Tab.insert(kind, formPars.getId(), new Struct(Struct.Array, currentType));
		} else {
			// deklaracija promenljive
			formParsNode = Tab.insert(kind, formPars.getId(), currentType);
		}

		int index = funcWithPars.indexOf(currentMethod.getName());
		// ako f-ja nije u nizu
		if (index == -1) {
			funcWithPars.add(currentMethod.getName());
			index = funcWithPars.indexOf(currentMethod.getName());
			formalPars.add(index, new ArrayList<Obj>());
		}

		// ubaci parametar u niz
		formalPars.get(index).add(formParsNode);
	}

	/****************** Expr *****************/
	@Override
	public void visit(Expr expr) {
		if (expr.getOptAddTerms() instanceof OptionalAddTerms) {
			if (expr.getExprTerm().struct != Tab.intType || expr.getOptAddTerms().struct != Tab.intType) {
				report_error("tipovi promenljivih prilikom sabiranja moraju biti tipa int", expr);
				return;
			}
		}

		expr.struct = expr.getExprTerm().struct;
	}
	
	@Override
	public void visit(ExpressionTerm expr) {
		if (expr.getOtpMinusExpr() instanceof NegativeExpr) {
			if (expr.getTerm().struct != Tab.intType) {
				report_error("izraz sa predznakom minus mora biti tipa int", expr);
				return;
			}
		}
		
		expr.struct = expr.getTerm().struct;
	}

	@Override
	public void visit(OptionalAddTerms addTerms) {
		addTerms.struct = addTerms.getTerm().struct;
	}

	/****************** Term *****************/
	@Override
	public void visit(Term term) {
		if (term.getOptMulFacts() instanceof OptionalMulFacts) {
			if (term.getFactor().struct != Tab.intType || term.getOptMulFacts().struct != Tab.intType) {
				report_error("tipovi promenljivih prilikom mnozenja moraju biti tipa int", term);
				return;
			}
		}

		term.struct = term.getFactor().struct;
	}

	@Override
	public void visit(OptionalMulFacts mulFacts) {
		mulFacts.struct = mulFacts.getFactor().struct;
	}

	/***************** Factor ****************/
	@Override
	public void visit(DesignFactor factor) {
		if (factor.getOptMethCall() instanceof OptionalMethCall) {
			// u pitanju je funkcija
			if (factor.getDesignator().obj.getKind() != Obj.Meth) {
				report_error("promenljiva " + factor.getDesignator().obj.getName() + " nije funkcija", factor);
				factor.struct = Tab.noType;
				return;
			}
		}
		
		if (factor.getOptMethCall() instanceof FactorMap) {
			// u pitanju je map
			if (factor.getDesignator().obj.getType().getKind() != Struct.Array) {
				report_error("promenljiva " + factor.getDesignator().obj.getName() + " nije niz", factor);
				factor.struct = Tab.noType;
				return;
			}
			
			if (factor.getDesignator().obj.getType().getElemType() != factor.getOptMethCall().obj.getType()) {
				report_error("promenljiva " + factor.getOptMethCall().obj.getName() + " nije istog tipa kao promenljiva " + factor.getDesignator().obj.getName(), factor);
				factor.struct = Tab.noType;
				return;
			}
		}

		factor.struct = factor.getDesignator().obj.getType();
	}

	@Override
	public void visit(FactorNum factor) {
		factor.struct = Tab.intType;
	}

	@Override
	public void visit(FactorChar factor) {
		factor.struct = Tab.charType;
	}

	@Override
	public void visit(FactorBool factor) {
		factor.struct = boolType;
	}

	@Override
	public void visit(FactorNewArray newArray) {
		if (newArray.getExpr().struct != Tab.intType) {
			report_error("velicina niza mora biti tipa int", newArray);
			newArray.struct = Tab.noType;
			return;
		}

		newArray.struct = new Struct(Struct.Array, newArray.getType().struct);
	}
	
	@Override
	public void visit(FactorNewMeth factor) {
		factor.struct = factor.getType().struct;
	}
	
	@Override
	public void visit(FactorExpr factor) {
		factor.struct = factor.getExpr().struct;
	}
	
	@Override
	public void visit(FactorNewMatrix factor) {
		if (factor.getExpr().struct != Tab.intType || factor.getExpr1().struct != Tab.intType) {
			report_error("velicina matrice mora biti tipa int", factor);
			factor.struct = Tab.noType;
			return;
		}

		factor.struct = new Struct(Struct.Array, factor.getType().struct);
	}
	
	@Override
	public void visit(FactorMap factor) {
		factor.obj = Tab.find(factor.getMapName().getId());
	}

	/**************** Designator **************/
	@Override
	public void visit(DesignVar var) {
		Obj varNode = Tab.find(var.getId());

		// promenljiva nije deklarisana
		if (varNode == Tab.noObj) {
			report_error("promenljiva " + var.getId() + " nije deklarisana", var);
			var.obj = Tab.noObj;
			return;
		}

		var.obj = varNode;
		//report_error("Ime ---> " + var.obj.getName() + " : Tip ---> " + var.obj.getType() + " : Elem Tip ---> " + var.obj.getType().getElemType(), null);
		
		if(varNode.getKind() == Obj.Meth) currentCallFunc = varNode;

		// ispis
		DumpSymbolTableVisitor stv = new DumpSymbolTableVisitor();
		stv.visitObjNode(var.obj);
		report_info("Pretraga na " + var.getLine() + "(" + var.getId() + "), nadjeno " + stv.getOutput(), null);
	}

	@Override
	public void visit(DesignArray array) {
		// promenljiva nije niz
		if (array.getDesignator() instanceof DesignVar) {
			if (array.getDesignator().obj.getType().getKind() != Struct.Array) {
				report_error("promenljiva " + array.getDesignator().obj.getName() + " nije niz", array);
				array.obj = Tab.noObj;
				return;
			}
			if (array.getExpr().struct != Tab.intType) {
				report_error("tip izraza za pristup nizu mora biti int", array);
				array.obj = Tab.noObj;
				return;
			}
		}
		array.getDesignator().obj = Tab.find(array.getDesignator().obj.getName());

		array.obj = new Obj(Obj.Elem, array.getDesignator().obj.getName(),
			array.getDesignator().obj.getType().getElemType());	

	}

	/**** ActPars ****/
	@Override
	public void visit(OptionalActPars actPars) {
		int index = funcWithPars.indexOf(currentCallFunc.getName());
		if (index != -1) {
			ArrayList<Obj> arr = formalPars.get(index);
			if (arr.size() != actualPars.size()) {
				report_error("broj formalnih i stvarnih parametara funkcije " + currentCallFunc.getName()
						+ " se razlikuje", actPars);
				actualPars.clear();
				return;
			}

			for (int i = 0; i < arr.size(); i++) {
				if (!arr.get(i).getType().compatibleWith(actualPars.get(i))) {
					report_error("formalni i stvarni parametari funkcije " + currentCallFunc.getName()
							+ " se razlikuju po tipu", actPars);
					actualPars.clear();
					return;
				}
			}
		}
		actualPars.clear();
	}

	@Override
	public void visit(MultipleActPars actPars) {
		// ubaci parametar u niz
		actualPars.add(actPars.getExpr().struct);
	}

	@Override
	public void visit(SingleActPars actPars) {
		// ubaci parametar u niz
		actualPars.add(actPars.getExpr().struct);
	}

	/*********** DesignatorStatement **********/
	@Override
	public void visit(DesignAssignOp assign) {

		//report_error(" ***** ----> " + assign.getDesignator().obj.getType(), null);
		//report_error(" ***** ----> " + assign.getDesignator().obj.getType().getElemType(), null);
		//report_error(" ***** ----> " + assign.getExpr().struct, null);
		if (!assign.getDesignator().obj.getType().compatibleWith(assign.getExpr().struct)) {
			// nisu kompatibilni
			report_error("tip promenljive " + assign.getDesignator().obj.getName() + " i izraza nisu kompatibilni",
					assign);
			return;
		}

		if (!(assign.getDesignator().obj.getKind() == Obj.Var || assign.getDesignator().obj.getKind() == Obj.Fld
				|| assign.getDesignator().obj.getKind() == Obj.Elem)) {
			// nije odgovarajuceg tipa
			report_error(assign.getDesignator().obj.getName()
					+ " mora biti ili promenljiva ili element niza ili element matrice ili polje klase", assign);
			return;
		}
	}

	@Override
	public void visit(IncEff inc) {
		if (!(inc.getDesignator().obj.getKind() == Obj.Var || inc.getDesignator().obj.getKind() == Obj.Fld
				|| inc.getDesignator().obj.getKind() == Obj.Elem)) {
			// nije odgovarajuceg tipa
			report_error(
					inc.getDesignator().obj.getName() + " mora biti ili promenljiva ili element niza ili element matrice ili polje klase",
					inc);
			return;
		}

		if (inc.getDesignator().obj.getType() != Tab.intType) {
			// nije odgovarajuceg tipa
			report_error(inc.getDesignator().obj.getName() + " mora biti tipa int", inc);
			return;
		}
	}

	@Override
	public void visit(DecEff dec) {
		if (!(dec.getDesignator().obj.getKind() == Obj.Var || dec.getDesignator().obj.getKind() == Obj.Fld
				|| dec.getDesignator().obj.getKind() == Obj.Elem)) {
			// nije odgovarajuceg tipa
			report_error(
					dec.getDesignator().obj.getName() + " mora biti ili promenljiva ili element niza ili element matrice ili polje klase",
					dec);
			return;
		}

		if (dec.getDesignator().obj.getType() != Tab.intType) {
			// nije odgovarajuceg tipa
			report_error(dec.getDesignator().obj.getName() + " mora biti tipa int", dec);
			return;
		}
	}

	@Override
	public void visit(ProcCall procCall) {
		if (procCall.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("promenljiva " + procCall.getDesignator().obj.getName() + " nije funkcija", procCall);
			return;
		}
	}

	@Override
	public void visit(MultipleAssign designStmt) {
		if (designStmt.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error(designStmt.getDesignator().obj.getName() + " mora biti niz", designStmt);
			return;
		}

		// kompatibilni?
		for (Obj elem : multipleAssignList) {
			if (!elem.getType().compatibleWith(designStmt.getDesignator().obj.getType().getElemType())) {
				report_error(elem.getName() + " nije kompatibilan sa " + designStmt.getDesignator().obj.getName(),
						designStmt);
			}
		}
	}

	@Override
	public void visit(OptDesign optDesign) {
		if (optDesign.getDesignator().obj.getKind() != Obj.Var && optDesign.getDesignator().obj.getKind() != Obj.Elem
				&& optDesign.getDesignator().obj.getKind() != Obj.Fld) {
			report_error(optDesign.getDesignator().obj.getName()
					+ " mora biti ili promenljiva ili element niza ili polje klase", optDesign);
			optDesign.obj = Tab.noObj;
			return;
		}

		// optDesign.obj = optDesign.getDesignator().obj;
		multipleAssignList.add(optDesign.getDesignator().obj);
	}

	/*
	 * @Override public void visit(MultipleDesign design) {
	 * 
	 * design.obj = design.getOptDesignator().obj; }
	 * 
	 * @Override public void visit(SingleDesign design) {
	 * 
	 * design.obj = design.getOptDesignator().obj; }
	 */

	/**************** Statement ****************/
	@Override
	public void visit(BreakStmt breakStmt) {
		if (!inWhile && !inForeach)
			report_error("iskaz 'break' se moze koristiti samo unutar while ili foreach petlje", breakStmt);
	}

	@Override
	public void visit(ContinueStmt continueStmt) {
		if (!inWhile && !inForeach)
			report_error("iskaz 'continue' se moze koristiti samo unutar while ili foreach petlje", continueStmt);
	}

	@Override
	public void visit(ReadStmt readStmt) {
		if (readStmt.getDesignator().obj.getKind() != Obj.Var && readStmt.getDesignator().obj.getKind() != Obj.Elem
				&& readStmt.getDesignator().obj.getKind() != Obj.Fld) {
			report_error(readStmt.getDesignator().obj.getName()
					+ " mora biti ili promenljiva ili element niza ili element matrice ili polje klase", readStmt);
			return;
		}

		if (readStmt.getDesignator().obj.getType() != Tab.intType
				&& readStmt.getDesignator().obj.getType() != Tab.charType
				&& readStmt.getDesignator().obj.getType() != boolType) {
			report_error(readStmt.getDesignator().obj.getName() + " mora biti ili int ili char ili bool tipa",
					readStmt);
			return;
		}
	}

	@Override
	public void visit(PrintStmt printStmt) {
		if (printStmt.getExpr().struct != Tab.intType && printStmt.getExpr().struct != Tab.charType
				&& printStmt.getExpr().struct != boolType) {
			report_error("izraz unutar print naredbe mora biti ili int ili char ili bool tipa", printStmt);
			return;
		}
	}

	/***** If *****/
	@Override
	public void visit(IfCond cond) {
		if (cond.getCondition().struct != boolType)
			report_error("uslov u if naredbi mora biti bool tipa", cond);
	}

	/**** Loop ****/
	@Override
	public void visit(WhileStmt whileStmt) {
		if (whileStmt.getCondition().struct != boolType)
			report_error("uslov u while naredbi mora biti bool tipa", whileStmt);

		inWhile = false;
	}

	@Override
	public void visit(WhileStart whileStart) {
		inWhile = true;
	}

	@Override
	public void visit(ForeachStmt foreachStmt) {
		if (foreachStmt.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error(foreachStmt.getDesignator().obj.getName() + " mora biti niz", foreachStmt);
			return;
		}

		if (foreachStmt.getForeachName().obj.getType() != foreachStmt.getDesignator().obj.getType().getElemType()) {
			report_error(foreachStmt.getDesignator().obj.getName() + " i " + foreachStmt.getForeachName().obj.getName()
					+ " moraju biti istog tipa", foreachStmt);
			return;
		}
	}

	@Override
	public void visit(ForeachName foreach) {
		Obj foreachNode = Tab.find(foreach.getId());

		if (foreachNode == Tab.noObj) {
			report_error("promenljiva" + foreach.getId() + " nije deklarisana", foreach);
			foreach.obj = Tab.noObj;
			return;
		} else {
			if (foreachNode.getKind() != Obj.Var) {
				report_error(foreach.getId() + " mora biti promenljiva", foreach);
				foreach.obj = Tab.noObj;
				return;
			}
		}

		foreach.obj = foreachNode;

		// ispis
		DumpSymbolTableVisitor stv = new DumpSymbolTableVisitor();
		stv.visitObjNode(foreach.obj);
		report_info("Pretraga na " + foreach.getLine() + "(" + foreach.getId() + "), nadjeno " + stv.getOutput(), null);

		inForeach = true;
	}

	@Override
	public void visit(ForeachEnd foreach) {
		inForeach = false;
	}

	/**************** Condition ***************/
	@Override
	public void visit(MultipleCondFact cond) {
		// kompatibilni tipovi
		if (!cond.getExpr().struct.compatibleWith(cond.getExpr1().struct)) {
			report_error("tipovi izraza u uslovu nisu kompatibilni", cond);
			cond.struct = Tab.noType;
			return;
		}
		// kompatibilni -> klase i nizovi?
		if (cond.getExpr().struct.getKind() == Struct.Array || cond.getExpr().struct.getKind() == Struct.Class) {
			if (!(cond.getRelop() instanceof EqualsOperation || cond.getRelop() instanceof NotEqualsOperation)) {
				report_error("uz klase i nizove dozvoljeni su samo '!=' i '==' operatori", cond);
				cond.struct = Tab.noType;
				return;
			}
		}

		cond.struct = boolType;
	}

	@Override
	public void visit(SingleCondFact cond) {
		if (cond.getExpr().struct != boolType) {
			report_error("izraz mora biti tipa bool", cond);
			cond.struct = Tab.noType;
		} else {
			cond.struct = boolType;
		}
	}

	@Override
	public void visit(MultipleCondTerms cond) {
		cond.struct = cond.getCondFact().struct;
	}

	@Override
	public void visit(SingleCondTerm cond) {
		cond.struct = cond.getCondFact().struct;
	}

	@Override
	public void visit(MultipleConditions cond) {
		cond.struct = cond.getCondTerm().struct;
	}

	@Override
	public void visit(SingleCondition cond) {
		cond.struct = cond.getCondTerm().struct;
	}	
	
	/****************** Map ******************/
	@Override
	public void visit(MapName name) {
		name.obj = Tab.find(name.getId());
		
		if(name.obj.getKind() != Obj.Var) {
			report_error(name.obj.getName() + " mora biti promenljiva", null);
		}
	}	

	/***************** Passed ****************/
	public boolean passed() {
		return !errorDetected;
	}
}
