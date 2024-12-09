package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adts.IHeap;
import model.adts.MyIMap;
import model.values.IValue;


//maybe I should make the exception runtime, or make 2 different exceptions like in controller

public interface IExpression {
    IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) throws ExpressionException;
    IExpression deepCopy();

}
