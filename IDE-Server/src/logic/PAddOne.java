package logic;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class PAddOne implements Command {
    private RuntimeStack stack;
    private Integer pointer;
    public PAddOne(RuntimeStack stk,MyInteger p){
        stack=stk;
        pointer=p.value;
    }
    @Override
    public void exec() {

    }

    @Override
    public void undo() {

    }
}
