package logic.remoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 * --RuntimeStack debug_*() ++String[] debug_*();
 */
public interface RuntimeI extends Remote {
    void run()throws RemoteException;
    String[] debug()throws RemoteException;
    String[] debugNext()throws RemoteException;
    String[] debugBack()throws RemoteException;
    void debugSetBreakpoint(int location)throws RemoteException;
    void setCurrentFile(String filename)throws RemoteException;
    void debugRemoveBreakpoint(int breakpoint)throws RemoteException;
    void terminate()throws RemoteException;
}
