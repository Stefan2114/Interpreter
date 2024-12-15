package model.statements;

import exceptions.*;
import model.adts.MyIMap;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.IType;
import model.values.IValue;


public class AssignStatement implements IStatement {


    private String variableName;
    private IExpression expression;

    public AssignStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }


    @Override
    public PrgState execute(PrgState prgState) {


        IValue prevValue = prgState.getSymTable().getValue(this.variableName);
        IValue newValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());;
        prgState.getSymTable().insert(this.variableName, newValue);
        return null;
    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {
        if(!(typeEnv.contains(this.variableName)))
            throw new TypeCheckException("Statement exceptions: the variable: " + this.variableName + " is not in the typeEnv");
        IType variableType = typeEnv.getValue(this.variableName);
        IType expressionType;
        ////// if expression returns null instead of throwing an exception i wouldn't need a try and catch
        try{
            expressionType = this.expression.typeCheck(typeEnv);
        } catch (TypeCheckExpressionException e) {
            throw new TypeCheckException("Expression exception: " + this.expression.toString() + " threw the exception: " + e.getMessage());

        }
        if(!(variableType.equals(expressionType)))
            throw new TypeCheckException("Statement exceptions: the variable " + this.variableName + " doesn't match the type of the expression " + this.expression.toString());


        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(new String(this.variableName), this.expression.deepCopy());
    }


    @Override
    public String toString() {
        return this.variableName + " = " + this.expression.toString() + ';';
    }
}

