package logic;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by xuxiangzhe on 2017/6/19.
 */
public class BFInterpreter implements Interpreter {
    private ArrayList<Command> commands=new ArrayList<>();
    private RuntimeStack stack=new RuntimeStack();
    private MyInteger index=new MyInteger(0);
    //to produce the jump command
    private ArrayList<MyInteger> jumpTable=new ArrayList<>();

    private IO io;
    public BFInterpreter(IO io){
        this.io=io;
    }
    @Override
    public ArrayList<Command> interpret(File source) {
        try {
            char next;
            Scanner sourceScanner=new Scanner(new FileInputStream(source));
            while(sourceScanner.hasNext()){
                next=(char)sourceScanner.nextByte();
                switch (next){
                    case '>':commands.add(new PAddOne(stack,index));break;
                    case '<':commands.add(new PSubOne(stack,index));break;
                    case '+':commands.add(new AddOne(stack,index));break;
                    case '-':commands.add(new SubOne(stack,index));break;
                    case '.':commands.add(new PrintWord(stack,index,io));break;
                    case ',':commands.add(new ReadWord(stack,index,io));break;
                    case '[':{
                        MyInteger x=new MyInteger(0);
                        jumpTable.add(0,x);
                        commands.add(new BEQZ(stack,index,x));
                        break;
                    }
                    case ']':{
                        commands.add(new BNEZ(stack,index,jumpTable.get(0)));
                        jumpTable.remove(0);
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return commands;
    }
    private void add(){
        for(MyInteger integer:jumpTable){
            integer.value+=1;
        }
    }

}
