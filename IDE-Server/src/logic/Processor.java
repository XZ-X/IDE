package logic;

import Data.MyInteger;
import logic.commands.Command;

import java.util.ArrayList;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class Processor {
    private ArrayList<Command> instructions;
    private MyInteger pointer,PC;
    private RuntimeStack stack;
    private Interpreter interpreter;
    private int breakpoint;
    public Processor(Interpreter p){
        interpreter=p;
        stack=interpreter.getStack();
        PC=interpreter.getPC();
        pointer=interpreter.getPointer();
        instructions=interpreter.getCommand();
    }
    private void initial(){
        pointer.value=0;
        PC.value=0;
        stack.clear();
    }

    public void exec(){
        initial();
        while (PC.value<=instructions.size()-1){
            instructions.get(PC.value).exec();
            PC.value++;
        }
    }

    public void debugExec(){
        initial();
        while (PC.value!=breakpoint){
            instructions.get(PC.value).exec();
            PC.value++;
        }
    }
    public void debugNext(){
        instructions.get(PC.value).exec();
        PC.value++;
    }

    public void debugBack(){
        if(PC.value!=0){
            PC.value--;
            instructions.get(PC.value).undo();
        }
    }

    public void setBreakpoint(int breakpoint) {
        this.breakpoint = breakpoint;
    }

    public int getBreakpoint() {
        return breakpoint;
    }

    public RuntimeStack getStack() {
        return stack;
    }

    public MyInteger getPC() {
        return PC;
    }

    public MyInteger getPointer() {
        return pointer;
    }


}
