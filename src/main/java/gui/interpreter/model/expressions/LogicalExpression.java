package gui.interpreter.model.expressions;

import gui.interpreter.exceptions.TypeCheckExpressionException;
import gui.interpreter.model.adts.IHeap;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.types.BoolType;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.values.BoolValue;
import gui.interpreter.model.values.IValue;

public class LogicalExpression implements IExpression {

    private IExpression leftExpression;
    private IExpression rightExpression;
    private LogicalOperator operator;

    public LogicalExpression(IExpression leftExpression, IExpression rightExpression, LogicalOperator operator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) {

        IValue value1 = this.leftExpression.evaluate(symTable, heap);
        IValue value2 = this.rightExpression.evaluate(symTable, heap);

        boolean boolValue1 = ((BoolValue) value1).getValue();
        boolean boolValue2 = ((BoolValue) value2).getValue();

        if (this.operator == LogicalOperator.AND) {
            return new BoolValue(boolValue1 && boolValue2);
        }

        return new BoolValue(boolValue1 || boolValue2);
    }

    @Override
    public IType typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckExpressionException {

        if (!(this.operator instanceof LogicalOperator))
            throw new TypeCheckExpressionException("The operator: " + this.operator + " from expression: "
                    + this.leftExpression.toString() + " is not a valid operator for a logic operation");

        IType type1 = this.leftExpression.typeCheck(typeEnv);
        if (!(type1.equals(new BoolType())))
            throw new TypeCheckExpressionException(
                    "First value: " + this.leftExpression.toString() + " is not of type BoolType");

        IType type2 = this.rightExpression.typeCheck(typeEnv);
        if (!(type2.equals(new BoolType())))
            throw new TypeCheckExpressionException(
                    "Second value: " + this.rightExpression.toString() + " is not of type BoolType");

        return new BoolType();

    }

    @Override
    public IExpression deepCopy() {
        return new LogicalExpression(this.leftExpression.deepCopy(), this.rightExpression.deepCopy(), this.operator);
    }

    @Override
    public String toString() {
        return this.leftExpression.toString() + " " + operator + " " + this.rightExpression.toString();
    }
}
