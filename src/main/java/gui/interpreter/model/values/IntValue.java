package gui.interpreter.model.values;

import gui.interpreter.model.types.IType;
import gui.interpreter.model.types.IntType;

public class IntValue implements IValue {

    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(this.value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof IntValue))
            return false;
        return ((IntValue) other).getValue() == this.value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }
}
