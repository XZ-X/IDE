package logic.commands;

import logic.remoteIneterfaces.IO;
import Data.MyInteger;
import logic.RuntimeStack;

import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class ReadWord implements Command {
    private RuntimeStack stack;
    private MyInteger pointer;
    private IO io;
    public ReadWord(RuntimeStack stk,MyInteger p,IO io){
        stack=stk;
        pointer=p;
        this.io=io;
    }

    @Override
    public void exec() {
        try {
            stack.add(pointer.value,io.getInput());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {
        stack.remove(pointer.value);
    }
}
