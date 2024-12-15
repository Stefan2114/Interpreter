package model.expressions;

import exceptions.ExpressionException;

import exceptions.TypeCheckExpressionException;
import model.adts.IHeap;
import model.adts.MyIMap;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapReadingExpression implements IExpression {

    private IExpression expression;

    public HeapReadingExpression(IExpression expression) {
        this.expression = expression;
    }


    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) {

        IValue value = this.expression.evaluate(symTable, heap);
        int address = ((RefValue) value).getAddress();
        if (!heap.contains(address))
            throw new ExpressionException("Variable " + value.toString() + " not found in the heap");

        IValue returnValue = heap.getValue(address);
        return returnValue;

    }

    @Override
    public IType typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckExpressionException {
        IType type = this.expression.typeCheck(typeEnv);
        if (!(type instanceof RefType))
            throw new TypeCheckExpressionException("The type of " + this.expression.toString() + " is not a RefType");
        return ((RefType) type).getInner();
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
