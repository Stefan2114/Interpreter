package gui.interpreter.model.statements;

import gui.interpreter.exceptions.TypeCheckException;
import gui.interpreter.model.adts.MyIMap;
import gui.interpreter.model.states.PrgState;
import gui.interpreter.model.types.IType;

public class CompStatement implements IStatement {

    private IStatement statement1;
    private IStatement statement2;

    public CompStatement(IStatement statement1, IStatement statement2) {
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    @Override
    public PrgState execute(PrgState prgState) {
        prgState.getExecStack().push(this.statement2);
        prgState.getExecStack().push(this.statement1);
        return null;
    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {

        MyIMap<String, IType> typeEnv1 = this.statement1.typeCheck(typeEnv);
        MyIMap<String, IType> typeEnv2 = this.statement2.typeCheck(typeEnv1);
        return typeEnv2;

    }

    @Override
    public IStatement deepCopy() {
        return new CompStatement(this.statement1.deepCopy(), this.statement2.deepCopy());
    }

    @Override
    public String toString() {
        return this.statement1.toString() + "\n" + this.statement2.toString();
    }
}
