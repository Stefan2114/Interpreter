package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.adts.MyIMap;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

///////////////////////////////////////////////////////////////////////////////////////////
public class ReadFileStatement implements IStatement {


    private IExpression expression;
    private String variableName;

    public ReadFileStatement(IExpression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }


//



    @Override
    public PrgState execute(PrgState prgState) throws StatementException {

        MyIMap<String, IValue> symTable = prgState.getSymTable();
        if (!symTable.contains(this.variableName))
            throw new StatementException("Variable: " + this.variableName + " was not found in the symTable");


        //now all adts throw runtime exception
        IValue variableValue = symTable.getValue(this.variableName);


        if (!variableValue.getType().equals(new IntType()))
            throw new StatementException("The variable: " + variableValue.toString() + " is not of IntType");


        IValue expressionValue;
        try{
            expressionValue = this.expression.evaluate(symTable, prgState.getHeap());
        }
        catch(ExpressionException e){
            throw new StatementException("The expression: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }

        if (!expressionValue.getType().equals(new StringType()))
            throw new StatementException("The result of the expression: " + this.expression.toString() + " is not a StringType");

        StringValue fileName = (StringValue) expressionValue;

        if (!prgState.getFileTable().contains(fileName))
            throw new StatementException("The file: " + fileName.toString() + "was not found in the fileTable");

        BufferedReader reader = prgState.getFileTable().getValue(fileName);

        try {
            String line = reader.readLine();
            if (line.isEmpty())
                line = "0";

            int parser = Integer.parseInt(line);

            symTable.insert(this.variableName, new IntValue(parser));

        } catch (IOException e) {
            throw new StatementException("Problem at reading from the file: " + fileName.toString());
        }
        return null;

    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(this.expression.deepCopy(), new String(this.variableName));
    }

    @Override
    public String toString() {
        return "readFile(" + this.expression.toString() + ", " + this.variableName + ");";
    }
}
