package logic;

import Data.MyInteger;
import logic.commands.Command;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class Processor implements Runnable{
    private ArrayList<Command> instructions;
    private Set<Integer> breakpoints=new HashSet<>();
    private MyInteger pointer,PC;
    private RuntimeStack stack;
    //control of concurrency
    private Thread thread;
    private boolean isRun=true;
    public void setInterpreter(Interpreter p){
        stack= p.getStack();
        PC= p.getPC();
        pointer= p.getPointer();
        instructions= p.getCommand();
    }



    private void initial(){
        pointer.value=0;
        PC.value=0;
        stack.clear();
        stop();
    }
    @Override
    synchronized public void run(){
        if(isRun) {
            while (PC.value <= instructions.size() - 1) {
                instructions.get(PC.value).exec();
                PC.value++;

//            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            }
        }
    }

    public void exec(){
        initial();
        //make sure the thread has been terminated
       if(thread== null||thread.getState()==Thread.State.TERMINATED) {
           isRun = true;
           thread = new Thread(this);
           thread.start();
       }
    }

    public void stop(){
        isRun=false;
    }
    public void debugExec(){
        initial();
        while (!breakpoints.contains(PC.value)){
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
        breakpoints.add(breakpoint);
    }

    public void removeBreakpoint(int breakpoint){
        breakpoints.remove(breakpoint);
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
