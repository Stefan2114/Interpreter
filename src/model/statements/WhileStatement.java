package model.statements;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

/////////////////////////////////////////////////////////////////////////

public class WhileStatement implements IStatement{

    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement){
        this.expression = expression;
        this.statement = statement;
    }



    //////////////////////////////////////////////////////should i push a deepCopy of the statements?
    @Override
    public PrgState execute(PrgState prgState) throws StatementException, KeyNotFoundException, ExpressionException {

        IValue expressionValue = this.expression.evaluate(prgState.getSymTable(),prgState.getHeap());
        if(!(expressionValue.getType().equals(new BoolType())))
            throw new StatementException("The value of expression: " + this.expression.toString() + " must be of BoolType");

        if(((BoolValue)expressionValue).getValue()){
            prgState.getExecStack().push(deepCopy());
            prgState.getExecStack().push(this.statement.deepCopy());
        }

        return prgState;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }


    @Override
    public String toString(){
        return "while(" + this.expression.toString() + ")\n{\n" + this.statement.toString() + "}";
    }
}
