package gui.interpreter.model.types;

import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.StringValue;

public class StringType implements IType {
    @Override
    public boolean equals(IType obj) {
        return obj instanceof StringType;
    }

    @Override
    public IValue getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "string";
    }
}
