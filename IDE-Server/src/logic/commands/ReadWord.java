package logic.commands;

import logic.remoteInterfaces.IO;
import Data.MyInteger;
import logic.RuntimeStack;

import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 * Read a word to the stack that the pointer point to.
 */
public class ReadWord implements Command {
    private RuntimeStack stack;
    private MyInteger pointer;
    private IO io;
    private int delete;
    public ReadWord(RuntimeStack stk,MyInteger p,IO io){
        stack=stk;
        pointer=p;
        this.io=io;
    }

    @Override
    public void exec() {
        try {
            delete=stack.replace(pointer.value,io.getInput());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {
        stack.replace(pointer.value,delete);
    }
}
