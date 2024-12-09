package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapWritingStatement implements IStatement{

    private String variableName;
    private IExpression expression;

    public HeapWritingStatement(String variableName,IExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }


    @Override
    public PrgState execute(PrgState prgState) throws StatementException, KeyNotFoundException {

        if(!(prgState.getSymTable().contains(this.variableName)))
            throw new StatementException(("The variable: " + this.variableName + " was not found in the symTable"));

        IValue variableValue = prgState.getSymTable().getValue(this.variableName);
        if(!(variableValue.getType() instanceof RefType))
            throw new StatementException("The variable: " + variableValue.toString() + " is not of type RefType");

        int address = ((RefValue)variableValue).getAddress();
        if(!(prgState.getHeap().contains(address)))
            throw new StatementException("The variable: " + variableValue.toString() + "  was not found on the heap");


        IValue expressionValue;
        try{
            expressionValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        }
        catch(ExpressionException e){
            throw new StatementException("The expression: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }

        if(!(((RefValue)variableValue).getLocationType().equals(expressionValue.getType())))
            throw new StatementException("The expression: " + expressionValue.toString() + " doesn't have the same type as the one stored at the address of the RefValue: " + variableValue.toString());

        prgState.getHeap().insert(address, expressionValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWritingStatement(new String(this.variableName), this.expression.deepCopy());
    }

    @Override
    public String toString(){
        return "wH("+this.variableName + ", " + this.expression.toString() + ");";
    }
}
