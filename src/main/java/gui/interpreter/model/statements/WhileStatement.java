package gui.interpreter.model.statements;

import gui.interpreter.exceptions.*;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.adts.MyMap;
import gui.interpreter.model.expressions.IExpression;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.BoolType;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.values.BoolValue;
import gui.interpreter.model.values.IValue;

/////////////////////////////////////////////////////////////////////////

public class WhileStatement implements IStatement {

    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }


    @Override
    public PrgState execute(PrgState prgState) throws StatementException {

        IValue expressionValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        if (((BoolValue) expressionValue).getValue()) {
            prgState.getExecStack().push(deepCopy());
            prgState.getExecStack().push(this.statement.deepCopy());
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
        if(!(expressionType.equals(new BoolType())))
            throw new TypeCheckException("Statement exception: the expression: " + this.expression.toString() + " is not of type BoolType");

        this.statement.typeCheck(new MyMap<String, IType>(typeEnv));
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }


    @Override
    public String toString() {
        return "while(" + this.expression.toString() + ")\n{\n" + this.statement.toString() + "}";
    }
}
