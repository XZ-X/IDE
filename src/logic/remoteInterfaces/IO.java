package logic.remoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

@SuppressWarnings("unused")
public interface IO extends Remote {
    char getInput()throws RemoteException;
    boolean outPut(char c) throws RemoteException;
}
