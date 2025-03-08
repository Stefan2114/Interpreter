package gui.interpreter.model.statements;

import gui.interpreter.exceptions.*;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.expressions.IExpression;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.types.IntType;
import gui.interpreter.model.types.StringType;
import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.IntValue;
import gui.interpreter.model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {

    private IExpression expression;
    private String variableName;

    public ReadFileStatement(IExpression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState prgState) throws StatementException {

        MyIMap<String, IValue> symTable = prgState.getSymTable();
        IValue expressionValue = this.expression.evaluate(symTable, prgState.getHeap());
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
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {

        if (!(typeEnv.contains(this.variableName)))
            throw new TypeCheckException(
                    "Statement exception: the variable: " + this.variableName + " is not in the typeEnv");

        IType variableType = typeEnv.getValue(this.variableName);
        if (!(variableType.equals(new IntType())))
            throw new TypeCheckException(
                    "Statement exception: the variable: " + this.variableName + " is not of type IntType");

        IType expressionType;
        try {
            expressionType = this.expression.typeCheck(typeEnv);
        } catch (TypeCheckExpressionException e) {
            throw new TypeCheckException(
                    "Expression exception: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }
        if (!(expressionType.equals(new StringType())))
            throw new TypeCheckException(
                    "Statement exception: the expression: " + this.expression.toString() + "is not of type StringType");
        return typeEnv;
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
