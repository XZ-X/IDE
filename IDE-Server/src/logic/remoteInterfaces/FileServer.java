package logic.remoteInterfaces;

import Data.GlobalConstant;
import Data.Language;
import Data.MyFile;
import Data.User;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

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
    public File[] lookupFile() throws RemoteException {
        ArrayList<MyFile> files=usr.getFile();
        File[] ret=new File[files.size()];
        int cnt=0;
        for(MyFile file:files){
            ret[cnt++]=file.open();
        }
        return ret;
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
        try {
            return usr.deleteFile(filename);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        currentFile.rename(newName);
    }

    @Override
    public Map<File, String> checkVersions(String fileName) throws RemoteException {
        return usr.getFile(fileName).getHistory();
    }


    @Override
    public boolean recovery(String rawFilename) throws RemoteException {
        rawFilename=rawFilename.split("\\.")[0];
        String[] fileInformation=rawFilename.split(GlobalConstant.FILE_NAME_SEPARATOR);
        usr.getFile(fileInformation[0]).setVersionTo(fileInformation[fileInformation.length-1]);
        return false;
    }
}
