package logic;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class BNEZ implements Command {
    private RuntimeStack stack;
    private Integer pointer;
    private Integer jumpLength;
    public BNEZ(RuntimeStack stk,MyInteger p,MyInteger length){
        stack=stk;
        pointer=p.value;
        jumpLength=length.value;
    }

    @Override
    public void exec() {

    }

    @Override
    public void undo() {

    }
}
