package logic.remoteInterfaces;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public interface MyFileI extends Remote{
    boolean creatFile(String language,String filename) throws RemoteException;
    File openFile(String filename) throws RemoteException;
    boolean deleteFile(String filename) throws RemoteException;
    String saveFile(String filename) throws RemoteException;
    void renameFile(String newName) throws RemoteException;
    File[] checkVersions() throws RemoteException;
    boolean recovery() throws RemoteException;
}
