package logic;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class PSubOne implements Command {
    private RuntimeStack stack;
    private Integer pointer;
    public PSubOne(RuntimeStack stk,MyInteger p){
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
