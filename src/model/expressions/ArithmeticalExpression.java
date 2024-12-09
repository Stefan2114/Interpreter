package model.expressions;

import exceptions.ExpressionException;
import exceptions.ExpressionRuntimeException;
import exceptions.KeyNotFoundException;
import model.adts.IHeap;
import model.adts.MyIMap;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticalExpression implements IExpression {

    private IExpression leftExpression;
    private IExpression rightExpression;
    private ArithmeticalOperator operator;


    public ArithmeticalExpression(IExpression leftExpression, IExpression rightExpression, ArithmeticalOperator operator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) throws ExpressionException {

        IValue value1 = this.leftExpression.evaluate(symTable, heap);
        IValue value2 = this.rightExpression.evaluate(symTable, heap);

        if (!value1.getType().equals(new IntType()))
            throw new ExpressionException("First value: " + this.leftExpression.toString() + " must be int");
        if (!value2.getType().equals(new IntType()))
            throw new ExpressionException("Second value: " + this.rightExpression.toString() + " must be int");


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
            case DIVIDE -> {
                if (intValue2 == 0)
                    throw new ExpressionRuntimeException("Division by 0 (" + toString() + ")");
                return new IntValue(intValue1 / intValue2);
            }
            default -> {
                throw new ExpressionException("Invalid operator: " + this.operator);
            }
        }

    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticalExpression(this.leftExpression.deepCopy(), this.rightExpression.deepCopy(), this.operator);
    }


    @Override
    public String toString() {
        return this.leftExpression.toString() + " " + operator + " " + this.rightExpression.toString();
    }
}
