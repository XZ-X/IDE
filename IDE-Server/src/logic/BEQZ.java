package logic;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class BEQZ implements Command {
    private RuntimeStack stack;
    private Integer pointer;
    private MyInteger jumpLength;
    public BEQZ(RuntimeStack stk,MyInteger p,MyInteger length){
        stack=stk;
        pointer=p.value;
        jumpLength=length;
    }


    @Override
    public void exec() {

    }

    @Override
    public void undo() {

    }
    @Override
    public String toString(){
        return super.toString()+"#####"+jumpLength;
    }
}
