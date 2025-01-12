package model.statements;

import exceptions.*;
import model.adts.MyIMap;
import model.adts.MyMap;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;


///////////////////////////////////////////////////////////////////////////
public class IfStatement implements IStatement {
    private IExpression expression;
    private IStatement thanStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thanStatement, IStatement elseIStatement) {
        this.expression = expression;
        this.thanStatement = thanStatement;
        this.elseStatement = elseIStatement;

    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException {

        IValue expressionValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        /////////////////////////////////////////////////////should i push a deepCopy of the statement?
        if (((BoolValue) expressionValue).getValue()) {
            prgState.getExecStack().push(this.thanStatement.deepCopy());
        } else {
            prgState.getExecStack().push(this.elseStatement.deepCopy());
        }

        return null;

    }



    ////////////////////////////////////////// how can i make this look better?
    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {
        IType expressionType;
        try{
            expressionType = this.expression.typeCheck(typeEnv);
        }catch(TypeCheckExpressionException e){
            throw new TypeCheckException("Expression exception: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }
        if(!(expressionType.equals(new BoolType())))
            throw new TypeCheckException("Statement exception: the expression: " + this.expression.toString() + " is not of type BoolType");

        this.thanStatement.typeCheck(new MyMap<String, IType>(typeEnv));
        this.elseStatement.typeCheck(new MyMap<String, IType>(typeEnv));

        return typeEnv;
    }


    @Override
    public IStatement deepCopy() {
        return new IfStatement(this.expression.deepCopy(), this.thanStatement.deepCopy(), this.elseStatement.deepCopy());
    }


    @Override
    public String toString() {
        return "if(" + this.expression.toString() + ")\n{" + this.thanStatement.toString() + "}\nelse{" + this.elseStatement.toString() + "}";

    }
}
