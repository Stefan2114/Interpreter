package model.expressions;

import model.adts.IHeap;
import model.adts.MyIMap;
import model.types.IType;
import model.values.IValue;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ValueExpression implements IExpression {

    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    ///////////////////////////////////////deepcopy
    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) {
        return this.value.deepCopy();

    }

    @Override
    public IType typeCheck(MyIMap<String, IType> typeEnv) {
        return this.value.getType();
    }

    //////////////////////////////////// deepcopy
    @Override
    public IExpression deepCopy() {
        return new ValueExpression(this.value.deepCopy());
    }


    @Override
    public String toString() {
        return this.value.toString();
    }
}
