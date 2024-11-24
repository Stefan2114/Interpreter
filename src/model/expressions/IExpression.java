package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adts.IHeap;
import model.adts.MyIMap;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) throws ExpressionException, KeyNotFoundException;
    IExpression deepCopy();

}
