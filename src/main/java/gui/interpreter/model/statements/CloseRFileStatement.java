package gui.interpreter.model.statements;

import gui.interpreter.exceptions.*;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.expressions.IExpression;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.types.StringType;
import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.StringValue;

import java.io.IOException;

public class CloseRFileStatement implements IStatement {

    private IExpression expression;

    public CloseRFileStatement(IExpression expression) {
        this.expression = expression;
    }


    @Override
    public PrgState execute(PrgState prgState) {

        IValue expressionValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());

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
        return new CloseRFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + this.expression.toString() + ");";
    }
}
