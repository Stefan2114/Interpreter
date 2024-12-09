package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.IOException;

public class CloseRFileStatement implements IStatement {

    private IExpression expression;

    public CloseRFileStatement(IExpression expression) {
        this.expression = expression;
    }


    @Override
    public PrgState execute(PrgState prgState) throws StatementException, KeyNotFoundException {

        IValue expressionValue;
        try{
            expressionValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        }
        catch(ExpressionException e){
            throw new StatementException("The expression: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }

        if (!expressionValue.getType().equals(new StringType()))
            throw new StatementException("The result of the expression: " + this.expression.toString() + " is not a StringType");

        StringValue fileName = (StringValue) expressionValue;
        if (!prgState.getFileTable().contains(fileName))
            throw new StatementException("The file: " + fileName.toString() + " was not found");

        try {
            prgState.getFileTable().getValue(fileName).close();
            prgState.getFileTable().remove(fileName);
        } catch (IOException e) {
            throw new StatementException("Problem at closing the file: " + fileName.toString());
        }
        return null;

    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + this.expression.toString() + ");";
    }
}
