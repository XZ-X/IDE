package logic;


import Data.MyFile;
import Data.MyInteger;
import logic.commands.*;
import logic.remoteInterfaces.IO;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by xuxiangzhe on 2017/6/19.
 * One of the core part of this project!
 *
 */
public class BFInterpreter implements Interpreter {
    private ArrayList<Command> commands=new ArrayList<>();
    private RuntimeStack stack=new RuntimeStack();
    private MyInteger pointer =new MyInteger(0);
    private MyInteger PC=new MyInteger(0);

    //to produce the jump command
    private ArrayList<MyInteger> jumpTable=new ArrayList<>();
    private IO io;
    private void add(){
        for(MyInteger integer:jumpTable){
            integer.value+=1;
        }
    }

    public BFInterpreter(IO io, MyFile file){
        this.io=io;
        interpret(file.open());
    }

    @Override
    public ArrayList<Command> interpret(File source) {
        try {
            String temp="";
            char next;
            Scanner sourceScanner=new Scanner(new FileInputStream(source));
            while(temp.length()!=0||sourceScanner.hasNext()){
                if(temp.length()==0) {
                    temp = sourceScanner.next();
                }
                next=temp.charAt(0);

                //this step is to guarantee the user's freedom of typing space:)
                if(temp.length()!=1) {
                    temp = temp.substring(1);
                }else {
                    temp="";
                }

                switch (next) {
                    case '>':
                        add();
                        commands.add(new PAddOne(stack, pointer));
                        break;
                    case '<':
                        add();
                        commands.add(new PSubOne(stack, pointer));
                        break;
                    case '+':
                        add();
                        commands.add(new AddOne(stack, pointer));
                        break;
                    case '-':
                        add();
                        commands.add(new SubOne(stack, pointer));
                        break;
                    case '.':
                        add();
                        commands.add(new PrintWord(stack, pointer, io));
                        break;
                    case ',':
                        add();
                        commands.add(new ReadWord(stack, pointer, io));
                        break;
                    case '[': {
                        add();
                        //the jump length of "BEQZ" is not specified until the BNEZ is found. So there exist a syntax check in the edit page on the client.
                        MyInteger x = new MyInteger(0);
                        jumpTable.add(0, x);
                        commands.add(new BEQZ(stack, pointer, x, PC));
                        break;
                    }
                    case ']': {
                        add();
                        commands.add(new BNEZ(stack, pointer, jumpTable.get(0), PC));
                        jumpTable.remove(0);
                        break;
                    }
                    default:
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return commands;
    }

    @Override
    public MyInteger getPC() {
        return PC;
    }

    @Override
    public RuntimeStack getStack() {
        return stack;
    }

    @Override
    public MyInteger getPointer() {
        return pointer;
    }

    @Override
    public ArrayList<Command> getCommand() {
        return commands;
    }

}
