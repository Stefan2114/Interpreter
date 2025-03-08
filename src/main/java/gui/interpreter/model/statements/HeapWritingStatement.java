package gui.interpreter.model.statements;

import gui.interpreter.exceptions.*;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.expressions.IExpression;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.types.RefType;
import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.RefValue;

public class HeapWritingStatement implements IStatement {

    private String variableName;
    private IExpression expression;

    public HeapWritingStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) {

        IValue variableValue = prgState.getSymTable().getValue(this.variableName);

        int address = ((RefValue) variableValue).getAddress();

        if (!(prgState.getHeap().contains(address)))
            throw new StatementException("The variable: " + variableValue.toString() + "  was not found on the heap");

        IValue expressionValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        prgState.getHeap().insert(address, expressionValue);
        return null;
    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {
        if (!(typeEnv.contains(this.variableName)))
            throw new TypeCheckException(
                    "Statement exception: the variable: " + this.variableName + " is not in the typeEnv");

        IType variableType = typeEnv.getValue(this.variableName);
        IType expressionType;
        try {
            expressionType = this.expression.typeCheck(typeEnv);
        } catch (TypeCheckExpressionException e) {
            throw new TypeCheckException(
                    "Expression exception: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }

        if (!(variableType.equals(new RefType(expressionType))))
            throw new TypeCheckException("Statement exception: the variable: " + this.variableName
                    + "and the expression: " + this.expression.toString() + " don't have the same type");

        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWritingStatement(new String(this.variableName), this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + this.variableName + ", " + this.expression.toString() + ");";
    }
}
