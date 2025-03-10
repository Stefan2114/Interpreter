package gui.interpreter.model.types;

import gui.interpreter.model.values.BoolValue;
import gui.interpreter.model.values.IValue;

public class BoolType implements IType {

    @Override
    public boolean equals(IType obj) {
        return obj instanceof BoolType;
    }

    @Override
    public IValue getDefaultValue() {
        return new BoolValue(false);
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return "bool";
    }
}
