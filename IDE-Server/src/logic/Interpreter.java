package logic;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public interface Interpreter {
    ArrayList<Command> interpret(File source);
    MyInteger getPC();
    RuntimeStack getStack();
    MyInteger getPointer();
    ArrayList<Command> getCommand();
}
