package logic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class FileServer extends UnicastRemoteObject implements MyFileI {
    private User usr;
    private MyFile currentFile;
    public FileServer(User user) throws RemoteException {
        usr=user;
    }

    @Override
    public boolean creatFile(String language, String filename) throws RemoteException {
        return false;
    }

    @Override
    public MyFile openFile(String filename) throws RemoteException {
        return null;
    }

    @Override
    public boolean deleteFile(String filename) throws RemoteException {
        return false;
    }

    @Override
    public String saveFile(String filename) throws RemoteException {
        return null;
    }

    @Override
    public void renameFile(String newName) throws RemoteException {

    }

    @Override
    public MyFile[] checkVersions() throws RemoteException {
        return new MyFile[0];
    }

    @Override
    public boolean recovery() throws RemoteException {
        return false;
    }
}
