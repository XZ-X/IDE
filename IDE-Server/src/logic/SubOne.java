package logic;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class SubOne implements Command {
    private RuntimeStack stack;
    private Integer pointer;
    public SubOne(RuntimeStack stk,Integer p){
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