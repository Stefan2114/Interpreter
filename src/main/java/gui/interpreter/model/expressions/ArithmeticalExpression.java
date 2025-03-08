package gui.interpreter.model.expressions;

import gui.interpreter.exceptions.ExpressionException;
import gui.interpreter.exceptions.TypeCheckExpressionException;
import gui.interpreter.model.adts.IHeap;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.types.IntType;
import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.IntValue;

public class ArithmeticalExpression implements IExpression {

    private IExpression leftExpression;
    private IExpression rightExpression;
    private ArithmeticalOperator operator;

    public ArithmeticalExpression(IExpression leftExpression, IExpression rightExpression,
            ArithmeticalOperator operator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) throws ExpressionException {

        IValue value1 = this.leftExpression.evaluate(symTable, heap);
        IValue value2 = this.rightExpression.evaluate(symTable, heap);
        int intValue1 = ((IntValue) value1).getValue();
        int intValue2 = ((IntValue) value2).getValue();

        switch (this.operator) {
            case ADD -> {
                return new IntValue(intValue1 + intValue2);
            }
            case SUBTRACT -> {
                return new IntValue(intValue1 - intValue2);
            }
            case MULTIPLY -> {
                return new IntValue(intValue1 * intValue2);
            }
            default -> {
                if (intValue2 == 0)
                    throw new ExpressionException("Division by 0 (" + toString() + ")");
                return new IntValue(intValue1 / intValue2);
            }
        }

    }

    @Override
    public IType typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckExpressionException {

        if (!(this.operator instanceof ArithmeticalOperator))
            throw new TypeCheckExpressionException("The operator: " + this.operator + " from expression: "
                    + this.leftExpression.toString() + " is not a valid operator for an arithmetical operation");

        IType type1 = this.leftExpression.typeCheck(typeEnv);
        if (!(type1.equals(new IntType())))
            throw new TypeCheckExpressionException(
                    "First value: " + this.leftExpression.toString() + " is not of type IntType");

        IType type2 = this.rightExpression.typeCheck(typeEnv);
        if (!(type2.equals(new IntType())))
            throw new TypeCheckExpressionException(
                    "Second value: " + this.rightExpression.toString() + " is not of type IntType");

        return new IntType();

    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticalExpression(this.leftExpression.deepCopy(), this.rightExpression.deepCopy(),
                this.operator);
    }

    @Override
    public String toString() {
        return this.leftExpression.toString() + " " + operator + " " + this.rightExpression.toString();
    }
}
