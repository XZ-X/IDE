package logic;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class AddOne implements Command {
    private RuntimeStack stack;
    private MyInteger pointer;
    public AddOne(RuntimeStack stk,MyInteger p){
        stack=stk;
        pointer=p;
    }
    @Override
    public void exec() {

    }

    @Override
    public void undo() {

    }
}
