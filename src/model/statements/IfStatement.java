package model.statements;

import com.sun.jdi.BooleanType;
import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;


///////////////////////////////////////////////////////////////////////////
public class IfStatement implements IStatement {
    private IExpression expression;
    private IStatement thanStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thanStatement, IStatement elseIStatement) {
        this.expression = expression;
        this.thanStatement = thanStatement;
        this.elseStatement = elseIStatement;

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


        if (!expressionValue.getType().equals(new BoolType())) {
            throw new StatementException("Expression: " + this.expression.toString() + " is not boolean");
        }

        /////////////////////////////////////////////////////should i push a deepCopy of the statement?
        if (((BoolValue) expressionValue).getValue()) {
            prgState.getExecStack().push(this.thanStatement.deepCopy());
        } else {
            prgState.getExecStack().push(this.elseStatement.deepCopy());
        }

        return null;

    }


    @Override
    public IStatement deepCopy() {
        return new IfStatement(this.expression.deepCopy(), this.thanStatement.deepCopy(), this.elseStatement.deepCopy());
    }


    @Override
    public String toString() {
        return "if(" + this.expression.toString() + ")\n{" + this.thanStatement.toString() + "}\nelse{" + this.elseStatement.toString() + "}";

    }
}
