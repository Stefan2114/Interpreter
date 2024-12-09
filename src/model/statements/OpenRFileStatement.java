package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class OpenRFileStatement implements IStatement {

    private IExpression expression;

    public OpenRFileStatement(IExpression expression) {
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

        if (!expressionValue.getType().equals(new StringType()))
            throw new StatementException("The result of the expression: " + this.expression.toString() + " is not a StringType");

        StringValue fileName = (StringValue) expressionValue;
        if (prgState.getFileTable().contains(fileName))
            throw new StatementException("The file: " + fileName.toString() + " is already open");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName.getValue()));
            prgState.getFileTable().insert(fileName, reader);
        } catch (IOException e) {
            throw new StatementException("Problem at opening the file: " + fileName.toString());
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "openRFile(" + this.expression.toString() + ");";
    }
}
