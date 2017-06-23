package logic.remoteIneterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public interface IO extends Remote {
    char getInput()throws RemoteException;
    boolean outPut(char c) throws RemoteException;
}
