package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.states.PrgState;
import model.values.IValue;

public class PrintStatement implements IStatement {

    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException {

        IValue expressionValue;
        try{
            expressionValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        }
        catch(ExpressionException e) {
            throw new StatementException("The expression: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }

        prgState.getOutputList().add(expressionValue.toString());
        return null;
    }


    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ");";
    }
}
