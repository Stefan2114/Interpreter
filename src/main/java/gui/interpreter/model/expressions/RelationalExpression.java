package gui.interpreter.model.expressions;

import gui.interpreter.exceptions.TypeCheckExpressionException;
import gui.interpreter.model.adts.IHeap;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.types.BoolType;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.types.IntType;
import gui.interpreter.model.values.BoolValue;
import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.IntValue;

public class RelationalExpression implements IExpression {

    private IExpression leftExpression;
    private IExpression rightExpression;
    private RelationalOperator operator;

    public RelationalExpression(IExpression lefExpression, IExpression rightExpression, RelationalOperator operator) {
        this.leftExpression = lefExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) {

        IValue value1 = this.leftExpression.evaluate(symTable, heap);
        IValue value2 = this.rightExpression.evaluate(symTable, heap);

        int intValue1 = ((IntValue) value1).getValue();
        int intValue2 = ((IntValue) value2).getValue();

        switch (this.operator) {
            case LessOrEqual -> {
                return new BoolValue(intValue1 <= intValue2);
            }
            case LessThan -> {
                return new BoolValue(intValue1 < intValue2);
            }
            case Equal -> {
                return new BoolValue(intValue1 == intValue2);
            }
            case GreaterThan -> {
                return new BoolValue(intValue1 > intValue2);
            }
            case GreaterOrEqual -> {
                return new BoolValue(intValue1 >= intValue2);
            }
            default -> {
                return new BoolValue(intValue1 != intValue2);
            }
        }
    }

    @Override
    public IType typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckExpressionException {

        if (!(this.operator instanceof RelationalOperator))
            throw new TypeCheckExpressionException("The operator: " + this.operator + " from expression: "
                    + this.leftExpression.toString() + " is not a valid operator for a relation operation");

        IType type1 = this.leftExpression.typeCheck(typeEnv);
        if (!(type1.equals(new IntType())))
            throw new TypeCheckExpressionException(
                    "First value: " + this.leftExpression.toString() + " is not of type IntType");

        IType type2 = this.rightExpression.typeCheck(typeEnv);
        if (!(type2.equals(new IntType())))
            throw new TypeCheckExpressionException(
                    "Second value: " + this.rightExpression.toString() + " is not of type IntType");

        return new BoolType();

    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(this.leftExpression.deepCopy(), this.rightExpression.deepCopy(), this.operator);
    }

    @Override
    public String toString() {
        return this.leftExpression.toString() + " " + this.operator + " " + this.rightExpression.toString();
    }

}
