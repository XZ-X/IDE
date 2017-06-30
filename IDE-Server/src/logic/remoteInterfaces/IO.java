package logic.remoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IO extends Remote {
    char getInput()throws RemoteException;
    boolean outPut(char c) throws RemoteException;
}
