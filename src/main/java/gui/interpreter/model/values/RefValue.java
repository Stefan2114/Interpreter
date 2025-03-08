package gui.interpreter.model.values;

import gui.interpreter.model.types.IType;
import gui.interpreter.model.types.RefType;

public class RefValue implements IValue {

    int address;
    IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RefValue))
            return false;
        return ((RefValue) obj).getAddress() == this.address
                && ((RefValue) obj).getLocationType().equals(this.locationType);
    }

    @Override
    public IType getType() {
        return new RefType(this.locationType);
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(this.address, this.locationType.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + this.address + "," + this.locationType.toString() + ")";
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.address);
    }
}
