package model.statements;

import exceptions.*;
import model.adts.MyIMap;
import model.expressions.IExpression;
import model.states.PrgState;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;


/////////////////////////////////////////////////////////////////////////////
public class HeapWritingStatement implements IStatement {

    private String variableName;
    private IExpression expression;

    public HeapWritingStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }


    @Override
    public PrgState execute(PrgState prgState) {


        IValue variableValue = prgState.getSymTable().getValue(this.variableName);

        int address = ((RefValue) variableValue).getAddress();

        //not sure if is necessarily because if it is in the symTable is in the heap because the garbage collector couldn't have deleted
        if (!(prgState.getHeap().contains(address)))
            throw new StatementException("The variable: " + variableValue.toString() + "  was not found on the heap");

        IValue expressionValue = this.expression.evaluate(prgState.getSymTable(), prgState.getHeap());
        prgState.getHeap().insert(address, expressionValue);
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
        return new HeapWritingStatement(new String(this.variableName), this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + this.variableName + ", " + this.expression.toString() + ");";
    }
}
