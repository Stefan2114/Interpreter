package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.states.PrgState;
import model.values.IValue;


public class AssignStatement implements IStatement {


    private String variableName;
    private IExpression expression;

    public AssignStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }


    @Override
    public PrgState execute(PrgState prgState) throws StatementException {

        if (!prgState.getSymTable().contains(this.variableName))
            throw new StatementException("Variable: " + this.variableName + "was not found in the symTable");

        IValue prevValue = prgState.getSymTable().getValue(this.variableName);
        IValue newValue;
        try{
            newValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        }catch(ExpressionException e){
            throw new StatementException("The expression: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }

        if (!prevValue.getType().equals(newValue.getType()))
            throw new StatementException("Value type does not match (" + prevValue.toString() + " != " + newValue.toString() + ")");

        prgState.getSymTable().insert(this.variableName, newValue);
        return null;
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

