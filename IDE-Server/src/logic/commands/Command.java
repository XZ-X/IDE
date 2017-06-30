package logic.commands;


public interface Command {
    void exec();
    void undo();
}
