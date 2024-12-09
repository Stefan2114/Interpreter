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
    public PrgState execute(PrgState prgState) throws StatementException {

        IValue expressionValue;
        try{
            expressionValue = this.expression.evaluate(prgState.getSymTable(),prgState.getHeap());
        }
        catch(ExpressionException e) {
            throw new StatementException("The expression: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }

        if(!(expressionValue.getType().equals(new BoolType())))
            throw new StatementException("The value of expression: " + this.expression.toString() + " must be of BoolType");

        if(((BoolValue)expressionValue).getValue()){
            prgState.getExecStack().push(deepCopy());
            prgState.getExecStack().push(this.statement.deepCopy());
        }

        return null;
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
