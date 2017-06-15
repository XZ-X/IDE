package logic;

import logic.RuntimeStack;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public interface RuntimeI extends Remote {
    void run()throws RemoteException;
    RuntimeStack debug()throws RemoteException;
    RuntimeStack debug_next()throws RemoteException;
    RuntimeStack debug_back()throws RemoteException;
    void debug_breakpoint(int location)throws RemoteException;
}
