package model.statements;

import exceptions.*;
import model.adts.MyIMap;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

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

        InputStream inputStream = OpenRFileStatement.class.getClassLoader().getResourceAsStream(fileName.getValue());
        if (inputStream == null)
            throw new StatementException("Problem at opening the file: " + fileName.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        prgState.getFileTable().insert(fileName, reader);

        return null;
    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {

        IType expressionType;
        try{
            expressionType = this.expression.typeCheck(typeEnv);
        }catch(TypeCheckExpressionException e){
            throw new TypeCheckException("Expression exception: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }
        if(!(expressionType.equals(new StringType())))
            throw new TypeCheckException("Statement exception: the expression: " + this.expression.toString() + "is not of type StringType");
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
