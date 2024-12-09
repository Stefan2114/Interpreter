package model.statements;


import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adts.MyIMap;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapAllocStatement implements IStatement {

    private String variableName;
    private IExpression expression;

    public HeapAllocStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException, KeyNotFoundException {

        MyIMap<String, IValue> symTable = prgState.getSymTable();
        if (!symTable.contains(this.variableName))
            throw new StatementException("The variable: " + this.variableName + " is not in the symTable");

        IValue variableValue = symTable.getValue(this.variableName);
        if (!(variableValue.getType() instanceof RefType))
            throw new StatementException("Variable: " + variableValue.toString() + " must be of type RefType");

        RefValue refValue = (RefValue) variableValue;

        IValue expressionValue;
        try{
            expressionValue = this.expression.evaluate(symTable, prgState.getHeap());
        }
        catch(ExpressionException e){
            throw new StatementException("The expression: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }

        if (!(expressionValue.getType().equals(refValue.getLocationType())))
            throw new StatementException("Expression: " + expressionValue.toString() + " type is not the same as the variable: " + refValue.toString() + " type");


        int address = prgState.getHeap().allocate(expressionValue);
        symTable.insert(this.variableName, new RefValue(address, expressionValue.getType()));
        return null;

    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocStatement(new String(this.variableName), this.expression.deepCopy());
    }


    @Override
    public String toString() {
        return "new("+this.variableName + ", " + this.expression.toString() + ");";
    }
}
