package ir;

import type.*;
import ast.*;
import java.util.Vector;

public interface TempVisitor {
    public Vector<IRFunction> getIRFuncs();
    public Temp visit (Program p);
    public Temp visit (Function f);
    public Temp visit (FunctionDeclaration fd, IRFunction irf);
    public Temp visit (FunctionBody fb, IRFunction irf);

    public Temp visit (AddExpression ad, IRFunction irf);
    public Temp visit (ArrayAssignmentStatement aa, IRFunction irf);
    public Temp visit (ArrayReferenceExpression ar, IRFunction irf);
    public Temp visit (AssignmentStatement as, IRFunction irf);
    public Temp visit (Block b, IRFunction irf) ;
    public Temp visit (EqualExpression ee, IRFunction irf);
    public Temp visit (ExpressionStatement es, IRFunction irf);
    public Temp visit (FormalParameter fp, IRFunction irf);
    public Temp visit (FunctionCallExpression ece, IRFunction irf);
    public Temp visit (Identifier i, IRFunction irf);
    public Temp visit (IdentifierReference ir, IRFunction irf);
    public Temp visit (IfStatement is, IRFunction irf);
    public Temp visit (LessthanExpression le, IRFunction irf);
    public Temp visit (MultiExpression me, IRFunction irf);
    public Temp visit (ParameterList pl, IRFunction irf);

    public Temp visit (ParenExpression pe, IRFunction irf);
    public Temp visit (PrintStatement ps, IRFunction irf);
    public Temp visit (PrintlnStatement pl, IRFunction irf);
    public Temp visit (ReturnStatement rs, IRFunction irf);
    public Temp visit (StatementList sl, IRFunction irf);
    public Temp visit (SubtractExpression se, IRFunction irf);
    //public Temp visit (TempNode t, IRFunction irf);
    public Temp visit (VariableDeclaration vd, IRFunction irf);
    public Temp visit (VariableDeclarationList vdl, IRFunction irf);
    public Temp visit (WhileStatement ws, IRFunction irf);
    public Temp visit (IntegerLiteral il, IRFunction irf);
    public Temp visit (StringLiteral sl, IRFunction irf);
    public Temp visit (FloatLiteral fl, IRFunction irf);
    public Temp visit (CharacterLiteral cl, IRFunction irf);
    public Temp visit (BooleanLiteral bl, IRFunction irf);

}
