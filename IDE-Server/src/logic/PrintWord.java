package logic;

import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class PrintWord implements Command {
    private RuntimeStack stack;
    private MyInteger pointer;
    private IO io;
    public PrintWord(RuntimeStack stk,MyInteger p,IO io){
        stack=stk;
        pointer=p;
        this.io=io;
    }

    @Override
    public void exec() {
        try {
            io.outPut((char)stack.get(pointer.value));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {
        //unnecessarily undo?
    }
}
