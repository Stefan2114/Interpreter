package gui.interpreter.model.values;

import gui.interpreter.model.types.IType;

public interface IValue {
    IType getType();

    IValue deepCopy();

}
