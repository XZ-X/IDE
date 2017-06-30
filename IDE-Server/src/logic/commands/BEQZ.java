package logic.commands;

import Data.MyInteger;
import logic.RuntimeStack;

public class BEQZ implements Command {
    private RuntimeStack stack;
    private MyInteger pointer;
    private MyInteger jumpLength;
    private MyInteger PC;
    public BEQZ(RuntimeStack stk,MyInteger p,MyInteger length,MyInteger PC){
        stack=stk;
        pointer=p;
        jumpLength=length;
        this.PC=PC;
    }


    @Override
    public void exec() {
        if(stack.get(pointer.value)==0)
        PC.value+=jumpLength.value;
    }

    @Override
    public void undo() {
        //Cannot undo
    }
    @Override
    public String toString(){
        return super.toString()+"#####"+jumpLength;
    }


}
