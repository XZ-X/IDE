package logic.commands;

import Data.MyInteger;
import logic.RuntimeStack;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class PAddOne implements Command {
    private RuntimeStack stack;
    private MyInteger pointer;
    public PAddOne(RuntimeStack stk,MyInteger p){
        stack=stk;
        pointer=p;
    }
    @Override
    public void exec() {
        pointer.value+=1;
    }

    @Override
    public void undo() {
        pointer.value-=1;
    }
}