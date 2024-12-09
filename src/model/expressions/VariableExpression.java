package model.expressions;

import exceptions.ExpressionException;
import exceptions.KeyNotFoundException;
import model.adts.IHeap;
import model.adts.MyIMap;
import model.values.IValue;

////////////////////////////////////////////////////////////////////////////////////
public class VariableExpression implements IExpression {

    private String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }


    @Override
    public IValue evaluate(MyIMap<String, IValue> symTable, IHeap heap) throws ExpressionException {
        /////////////////////////////////////////////////////// should be deepcopy?
        if(!(symTable.contains(this.variableName)))
            throw new ExpressionException("Variable: " + this.variableName + "was not found in the symTable");
        return symTable.getValue(this.variableName).deepCopy();
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
