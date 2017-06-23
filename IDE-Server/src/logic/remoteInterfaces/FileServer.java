package logic.remoteInterfaces;

import Data.Language;
import Data.MyFile;
import logic.User;
import logic.remoteInterfaces.MyFileI;

import java.io.File;
import java.io.IOException;
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
    public boolean createFile(Language language, String filename) throws RemoteException {
        try {
            usr.addFile(new MyFile(language,filename,usr));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public File openFile(String filename) throws RemoteException {
        MyFile file=usr.getFile(filename);
        File ret=null;
        if(file!=null){
            ret=file.open();
            currentFile=file;
        }
        return ret;
    }

    @Override
    public boolean deleteFile(String filename) throws RemoteException {
        return false;
    }

    @Override
    public String saveFile(String contents) throws RemoteException {
        try {
            currentFile.save(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void renameFile(String newName) throws RemoteException {

    }

    @Override
    public File[] checkVersions() throws RemoteException {
        return new File[0];
    }

    @Override
    public boolean recovery() throws RemoteException {
        return false;
    }
}
