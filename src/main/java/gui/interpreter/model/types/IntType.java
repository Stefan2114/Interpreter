package gui.interpreter.model.types;

import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.IntValue;

public class IntType implements IType {


    @Override
    public boolean equals(IType obj) {
        return obj instanceof IntType;
    }

    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public String toString() {
        return "int";
    }
}
