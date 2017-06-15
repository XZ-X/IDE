package logic;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class BEQZ implements Command {
    private RuntimeStack stack;
    private Integer pointer;
    private int jumpLength;
    public BEQZ(RuntimeStack stk,Integer p,int length){
        stack=stk;
        pointer=p;
        jumpLength=length;
    }


    @Override
    public void exec() {

    }

    @Override
    public void undo() {

    }
}
