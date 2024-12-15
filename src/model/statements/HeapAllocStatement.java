package model.statements;


import exceptions.*;
import model.adts.MyIMap;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapAllocStatement implements IStatement {

    private String variableName;
    private IExpression expression;

    public HeapAllocStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState){

        MyIMap<String, IValue> symTable = prgState.getSymTable();
        IValue expressionValue = this.expression.evaluate(symTable, prgState.getHeap());
        int address = prgState.getHeap().allocate(expressionValue);
        symTable.insert(this.variableName, new RefValue(address, expressionValue.getType()));
        return null;

    }

    @Override
    public MyIMap<String, IType> typeCheck(MyIMap<String, IType> typeEnv) throws TypeCheckException {
        if(!(typeEnv.contains(this.variableName)))
            throw new TypeCheckException("Statement exception: the variable: " + this.variableName + " is not in the typeEnv");

        IType variableType = typeEnv.getValue(this.variableName);
        IType expressionType;
        try{
            expressionType = this.expression.typeCheck(typeEnv);
        }catch(TypeCheckExpressionException e){
            throw new TypeCheckException("Expression exception: " + this.expression.toString() + " threw the exception: " + e.getMessage());
        }

        if(!(variableType.equals(new RefType(expressionType))))
            throw new TypeCheckException("Statement exception: the variable: " + this.variableName + "and the expression: " + this.expression.toString() + " don't have the same type");

        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocStatement(new String(this.variableName), this.expression.deepCopy());
    }


    @Override
    public String toString() {
        return "new(" + this.variableName + ", " + this.expression.toString() + ");";
    }
}
