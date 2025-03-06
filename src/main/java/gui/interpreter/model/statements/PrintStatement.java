package gui.interpreter.model.statements;

import gui.interpreter.exceptions.*;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.expressions.IExpression;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.values.IValue;

public class PrintStatement implements IStatement {

    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) {

        IValue expressionValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        prgState.getOutputList().add(expressionValue.toString());
        return null;
    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {

        try {
            this.expression.typeCheck(typeEnv);
        }catch (TypeCheckExpressionException e) {
            throw new TypeCheckException("Expression exception: " + this.expression.toString() + "threw: " + e.getMessage());
        }

        return typeEnv;
    }


    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ");";
    }
}
