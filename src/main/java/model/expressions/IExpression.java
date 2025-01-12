package model.expressions;

import exceptions.TypeCheckExpressionException;
import model.adts.IHeap;
import model.adts.MyIMap;
import model.types.IType;
import model.values.IValue;


//maybe I should make the exception runtime, or make 2 different exceptions like in controller

public interface IExpression {
    IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap);
    IType typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckExpressionException;
    IExpression deepCopy();


}
