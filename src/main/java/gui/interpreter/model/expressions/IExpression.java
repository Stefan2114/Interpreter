package gui.interpreter.model.expressions;

import gui.interpreter.exceptions.TypeCheckExpressionException;
import gui.interpreter.model.adts.IHeap;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.values.IValue;

public interface IExpression {
    IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap);

    IType typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckExpressionException;

    IExpression deepCopy();

}
