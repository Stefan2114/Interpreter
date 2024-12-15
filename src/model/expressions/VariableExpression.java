package model.expressions;

import exceptions.TypeCheckExpressionException;
import model.adts.IHeap;
import model.adts.MyIMap;
import model.types.IType;
import model.values.IValue;

////////////////////////////////////////////////////////////////////////////////////
public class VariableExpression implements IExpression {

    private String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }


    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) {
        /////////////////////////////////////////////////////// should be deepcopy?
        return symTable.getValue(this.variableName).deepCopy();
    }

    @Override
    public IType typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckExpressionException {
        /////////////////////////////////////////////////////// could I just replace the thrown exception with a return null???
        if(!(typeEnv.contains(this.variableName)))
            throw new TypeCheckExpressionException(this.variableName + " was not found in the typeEnv");
        return typeEnv.getValue(this.variableName);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(new String(this.variableName));
    }

    @Override
    public String toString() {
        return this.variableName;
    }
}
