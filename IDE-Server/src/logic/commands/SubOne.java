package logic.commands;

import Data.MyInteger;
import logic.RuntimeStack;

public class SubOne implements Command {
    private RuntimeStack stack;
    private MyInteger pointer;
    public SubOne(RuntimeStack stk,MyInteger p){
        stack=stk;
        pointer=p;
    }

    @Override
    public void exec() {
        stack.replace(pointer.value,stack.get(pointer.value)-1);
    }

    @Override
    public void undo() {
        stack.replace(pointer.value,1+stack.get(pointer.value));
    }
}
