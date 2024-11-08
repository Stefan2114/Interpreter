package model.values;

import model.types.IType;
import model.types.IntType;

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
    public boolean equals(IValue other) {
        return (other instanceof IntValue) && (((IntValue) other).getValue() == this.value);
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
