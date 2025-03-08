package gui.interpreter.model.expressions;

import gui.interpreter.model.adts.IHeap;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.types.IType;
import gui.interpreter.model.values.IValue;

public class ValueExpression implements IExpression {

    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) {
        return this.value.deepCopy();

    }

    @Override
    public IType typeCheck(MyIMap<String, IType> typeEnv) {
        return this.value.getType();
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(this.value.deepCopy());
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
