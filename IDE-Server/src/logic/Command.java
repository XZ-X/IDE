package logic;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public interface Command {
    void exec();
    void undo();
}
