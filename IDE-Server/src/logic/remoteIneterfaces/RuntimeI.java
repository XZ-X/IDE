package logic.remoteIneterfaces;

import logic.RuntimeStack;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 * --RuntimeStack debug_*() ++String[] debug_*();
 */
public interface RuntimeI extends Remote {
    void run()throws RemoteException;
    String[] debug()throws RemoteException;
    String[] debug_next()throws RemoteException;
    String[] debug_back()throws RemoteException;
    void debug_breakpoint(int location)throws RemoteException;
}
