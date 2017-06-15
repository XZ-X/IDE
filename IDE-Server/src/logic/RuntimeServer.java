package logic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class RuntimeServer extends UnicastRemoteObject implements RuntimeI {
    private MyFile currentFile;
    private User usr;
    public RuntimeServer(User user,MyFile file)throws RemoteException{
        usr=user;
        currentFile=file;
    }
    @Override
    public void run() throws RemoteException {

    }

    @Override
    public RuntimeStack debug() throws RemoteException {
        return null;
    }

    @Override
    public void debug_breakpoint(int location) throws RemoteException {

    }

    @Override
    public RuntimeStack debug_back() throws RemoteException {
        return null;
    }

    @Override
    public RuntimeStack debug_next() throws RemoteException {
        return null;
    }
}
