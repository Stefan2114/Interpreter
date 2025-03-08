package gui.interpreter.model.statements;

import gui.interpreter.exceptions.TypeCheckException;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;

public class VarDeclStatement implements IStatement {

    private String variableName;
    private IType type;

    public VarDeclStatement(String variableName, IType type) {
        this.variableName = variableName;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState prgState) {
        prgState.getSymTable().insert(this.variableName, this.type.getDefaultValue());
        return null;
    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {
        if (typeEnv.contains(this.variableName))
            throw new TypeCheckException(
                    "Statement exception: a variable with the same name: " + this.variableName + " already exists");
        typeEnv.insert(this.variableName, this.type);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStatement(new String(this.variableName), this.type.deepCopy());
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.variableName + ';';
    }

}
