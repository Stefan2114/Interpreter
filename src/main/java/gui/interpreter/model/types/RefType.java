package gui.interpreter.model.types;

import gui.interpreter.model.values.IValue;
import gui.interpreter.model.values.RefValue;

public class RefType implements IType {

    private IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(IType obj) {
        return obj instanceof RefType && ((RefType) obj).getInner().equals(this.inner);
    }

    @Override
    public IValue getDefaultValue() {
        return new RefValue(0, this.inner);
    }

    @Override
    public IType deepCopy() {
        return new RefType(this.inner.deepCopy());
    }

    @Override
    public String toString() {
        return "Ref " + this.inner.toString();
    }
}
