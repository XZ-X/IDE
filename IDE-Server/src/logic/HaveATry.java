package logic;

import logic.commands.Command;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by xuxiangzhe on 2017/6/19.
 */
public class HaveATry {
    public static void main(String[] args) throws IOException {
         File file=new File("a.bf");
        try {
            FileWriter writer=new FileWriter(file,false);
            writer.write("[->++++++<]>++ +++++ .<");
            writer.flush();
            BFInterpreter interpreter=new BFInterpreter(null);
            ArrayList<Command> arrayList=interpreter.interpret(file);
            System.out.println(arrayList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
