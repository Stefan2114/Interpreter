package gui.interpreter.model.statements;

import gui.interpreter.exceptions.*;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.expressions.IExpression;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.types.StringType;
import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.StringValue;

import java.io.*;

public class OpenRFileStatement implements IStatement {

    private IExpression expression;

    public OpenRFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) {

        IValue expressionValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());

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
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {

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
        return new OpenRFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "openRFile(" + this.expression.toString() + ");";
    }
}
