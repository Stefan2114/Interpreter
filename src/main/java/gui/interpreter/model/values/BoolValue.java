package gui.interpreter.model.values;

import gui.interpreter.model.types.BoolType;
import gui.interpreter.model.types.IType;


public class BoolValue implements IValue {

    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }


    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(this.value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BoolValue))
            return false;
        return ((BoolValue) other).getValue() == this.value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(this.value);
    }
}
