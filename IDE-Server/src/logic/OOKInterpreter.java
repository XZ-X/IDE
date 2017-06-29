package logic;

import Data.GlobalConstant;
import Data.MyFile;
import Data.MyInteger;
import logic.commands.*;
import logic.remoteInterfaces.IO;
import serverUtilities.FileTools;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The advance of OOP design pattern: I only take five minutes to finish this class after building a BF IDE.
 */
public class OOKInterpreter implements Interpreter {
    private ArrayList<Command> commands=new ArrayList<>();
    private RuntimeStack stack=new RuntimeStack();
    private MyInteger pointer =new MyInteger(0);
    private MyInteger PC=new MyInteger(0);
    //to produce the jump command
    private ArrayList<MyInteger> jumpTable=new ArrayList<>();
    private IO io;
    public OOKInterpreter(IO io, MyFile file){
        this.io=io;
        interpret(file.open());
    }
    @Override
    public ArrayList<Command> interpret(File source) {
        String src= FileTools.convertF2S(source);
        src=src.replaceAll(" ","");
        Pattern pattern=Pattern.compile(GlobalConstant.OOK_SYNTAX);
        Matcher matcher=pattern.matcher(src);
        StringBuffer command=new StringBuffer();
        while(matcher.find()){
            command.setLength(0);
            command.append(matcher.group(2));
            command.append(matcher.group(3));

            switch (new String(command)){
                case ".?":
                    add();
                    commands.add(new PAddOne(stack, pointer));
                    break;
                case "?.":
                    add();
                    commands.add(new PSubOne(stack, pointer));
                    break;
                case "..":
                    add();
                    commands.add(new AddOne(stack, pointer));
                    break;
                case "!!":
                    add();
                    commands.add(new SubOne(stack, pointer));
                    break;
                case "!.":
                    add();
                    commands.add(new PrintWord(stack, pointer, io));
                    break;
                case ".!":
                    add();
                    commands.add(new ReadWord(stack, pointer, io));
                    break;
                case "!?": {
                    add();
                    MyInteger x = new MyInteger(0);
                    jumpTable.add(0, x);
                    commands.add(new BEQZ(stack, pointer, x, PC));
                    break;
                }
                case "?!": {
                    add();
                    commands.add(new BNEZ(stack, pointer, jumpTable.get(0), PC));
                    jumpTable.remove(0);
                    break;
                }
                default:
            }
        }
        return null;
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

    private void add(){
        for(MyInteger integer:jumpTable){
            integer.value+=1;
        }
    }
}
