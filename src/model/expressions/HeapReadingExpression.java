package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adts.IHeap;
import model.adts.MyIMap;
import model.states.PrgState;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapReadingExpression implements IExpression {

    private IExpression expression;

    public HeapReadingExpression(IExpression expression) {
        this.expression = expression;
    }


    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) throws ExpressionException {

        IValue value = this.expression.evaluate(symTable, heap);
        if (!(value.getType() instanceof RefType))
            throw new ExpressionException("The expression " + this.expression.toString() + " result is not of type RefType");
        int address = ((RefValue) value).getAddress();
        if (!heap.contains(address))
            throw new ExpressionException("Variable " + value.toString() + " not found in the heap");

        IValue returnValue = heap.getValue(address);
        return returnValue;

    }

    @Override
    public IExpression deepCopy() {
        return new HeapReadingExpression(this.expression.deepCopy());
    }


    @Override
    public String toString() {
        return "rH(" + this.expression.toString() + ")";
    }
}
