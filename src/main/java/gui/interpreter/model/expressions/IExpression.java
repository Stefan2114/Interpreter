package gui.interpreter.model.expressions;

import gui.interpreter.exceptions.TypeCheckExpressionException;
import gui.interpreter.model.adts.IHeap;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.values.IValue;


//maybe I should make the exception runtime, or make 2 different exceptions like in controller

public interface IExpression {
    IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap);
    IType typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckExpressionException;
    IExpression deepCopy();


}
