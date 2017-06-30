package logic.commands;

import Data.MyInteger;
import logic.RuntimeStack;


public class PSubOne implements Command {
    private RuntimeStack stack;
    private MyInteger pointer;
    public PSubOne(RuntimeStack stk,MyInteger p){
        stack=stk;
        pointer=p;
    }

    @Override
    public void exec() {
        pointer.value-=1;
    }

    @Override
    public void undo() {
        pointer.value+=1;
    }
}
