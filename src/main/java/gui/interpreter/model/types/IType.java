package gui.interpreter.model.types;

import gui.interpreter.model.values.IValue;

public interface IType {
    boolean equals(IType obj);

    IValue getDefaultValue();
    IType deepCopy();
}
