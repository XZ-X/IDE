package logic;

import Data.GlobalConstant;
import Data.MyInteger;
import logic.commands.Command;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by xuxiangzhe on 2017/6/15.
 * This class will function as the CPU of each user which can execute the 'assemble language' of BF and OOK.
 * Also, the processor has a 'stack'.
 * Users can even use the debug function as they're using the authentic processor.
 */
public class Processor implements Runnable,Callable<String[]>{
    private ArrayList<Command> instructions;
    //helpers
    private LinkedHashSet<Integer> breakpoints=new LinkedHashSet<>();
    private MyInteger pointer,PC;
    private RuntimeStack stack;
    //control of concurrency
    private Thread thread;
    private boolean isRun=true;
    //record the trail for debug
    private Stack<Integer> PCStack=new Stack<>();

    //Why I don't make this method a constructor?
    //Because I want to reuse this processor giving that a user compile two different files at one time.
    public void setInterpreter(Interpreter p){
        stack= p.getStack();
        PC= p.getPC();
        pointer= p.getPointer();
        instructions= p.getCommand();
    }


    //Reset pointer and PC, clear the stack.
    private void initial(){
        stop();
        pointer.value=0;
        PC.value=0;
        stack.clear();
        PCStack.clear();
    }

    //This method is for the normal_run thread.
    @Override
    synchronized public void run() {
        if (isRun) {

            while (PC.value <= instructions.size() - 1) {
                instructions.get(PC.value).exec();
                PC.value++;
            }
        }
    }

    //This method is for the debug_run thread, featuring in running until reach a breakpoint, after which the method will print the stack.
    @Override
    public String[] call() throws Exception {
        if(isRun) {
            while (!breakpoints.contains(PC.value) && PC.value < instructions.size()) {
                PCStack.push(PC.value);
                instructions.get(PC.value).exec();
                PC.value++;
            }
        }
        //if the processor has finished this programme, PC should be marked, otherwise it will cause OutOfBound in client.
        return (((PC.value==instructions.size())?GlobalConstant.DEBUG_FINISH:PC.value)
                +","+pointer.value+","
                +stack.toString()).replaceAll("[\\[\\]]","").split(",");
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
    //Stop current thread, no matter it's debug thread or run thread.
    public void stop(){
        isRun=false;
    }
    public String[] debugExec(){
        initial();
        FutureTask<String[]> debugTask=new FutureTask<>(this);
        //If the programme isn't finished in 10 seconds, a TIME_OUT message will be delivered.
        //If the debug thread runs into other faults, a DEBUG_WRONG message will be delivered.
        if(thread== null||thread.getState()==Thread.State.TERMINATED){
            isRun=true;
            thread=new Thread(debugTask);
            thread.start();
            try {
                return debugTask.get(GlobalConstant.DEBUG_TIME_OUT, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                return new String[]{GlobalConstant.DEBUG_WRONG_MESSAGE};
            } catch (ExecutionException e) {
                return new String[]{GlobalConstant.DEBUG_WRONG_MESSAGE};
            } catch (TimeoutException e) {
                return new String[]{GlobalConstant.DEBUG_TIME_OUT_MESSAGE};
            }
        }else {
            return debugExec();
        }
    }

//    public String[] debugExec(){
//        initial();
//        while (!breakpoints.contains(PC.value) && PC.value < instructions.size()) {
//            instructions.get(PC.value).exec();
//            PC.value++;
//        }
//        return (((PC.value==instructions.size())?"finish":PC.value)+","+pointer.value+","+stack.toString()).replaceAll("[\\[\\]]","").split(",");
//    }
    public  String[] debugNext(){
        if(PC.value<instructions.size()) {
            PCStack.push(PC.value);
            instructions.get(PC.value).exec();
            PC.value++;
        }
        return (((PC.value==instructions.size())?GlobalConstant.DEBUG_FINISH:PC.value)
                +","+pointer.value+","
                +stack.toString()).replaceAll("[\\[\\]]","").split(",");
    }

    public  String[] debugBack(){
        if(PC.value>0){
            PC.value=PCStack.pop();
            instructions.get(PC.value).undo();
        }
        return (((PC.value==instructions.size())?GlobalConstant.DEBUG_FINISH:PC.value)
                +","+pointer.value+","
                +stack.toString()).replaceAll("[\\[\\]]","").split(",");
    }

    public String[] debugContinue(){
        isRun=false;
        FutureTask<String[]> debugTask=new FutureTask<>(this);
        if(thread== null||thread.getState()==Thread.State.TERMINATED){
            isRun=true;
            thread=new Thread(debugTask);
            thread.start();
            try {
                return debugTask.get(GlobalConstant.DEBUG_TIME_OUT, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                return new String[]{GlobalConstant.DEBUG_WRONG_MESSAGE};
            } catch (ExecutionException e) {
                return new String[]{GlobalConstant.DEBUG_WRONG_MESSAGE};
            } catch (TimeoutException e) {
                return new String[]{GlobalConstant.DEBUG_TIME_OUT_MESSAGE};
            }
        }else {
            return debugContinue();
        }
    }

    public void setBreakpoint(int breakpoint) {
        breakpoints.add(breakpoint);
    }

    public void removeBreakpoint(int breakpoint){
        if(breakpoints.contains(breakpoint)) {
            breakpoints.remove(breakpoint);
        }
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
