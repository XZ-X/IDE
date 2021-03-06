package logic.remoteInterfaces;

import Data.Language;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;


public interface MyFileI extends Remote{
    boolean createFile(Language language, String filename) throws RemoteException;
    File[] lookupFile()throws RemoteException;
    File openFile(String filename) throws RemoteException;
    boolean deleteFile(String filename) throws RemoteException;
    String saveFile(String content) throws RemoteException;
    void renameFile(String newName) throws RemoteException;
    Map<File,String> checkVersions(String fileName) throws RemoteException;
    boolean recovery(String rawFilename) throws RemoteException;
    String getLastLogIn() throws RemoteException;
}
