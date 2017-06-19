package logic;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class ReadWord implements Command {
    private RuntimeStack stack;
    private Integer pointer;
    private IO io;
    public ReadWord(RuntimeStack stk,MyInteger p,IO io){
        stack=stk;
        pointer=p.value;
        this.io=io;
    }

    @Override
    public void exec() {

    }

    @Override
    public void undo() {

    }
}
