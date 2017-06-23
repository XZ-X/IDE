package logic.remoteIneterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public interface AccountI extends Remote {
    String login(String id, String passwd) throws RemoteException;
    String signUp(String id, String passwd, String secureQuestion, String answer)throws RemoteException;
    String logOut() throws RemoteException;
    String forgetPassword()throws RemoteException;
    String resetPassword(String answer, String password)throws RemoteException;
    boolean setPreferences(int versionNumber, boolean isAutoSave, int autoSaveTime)throws RemoteException;
    void startIO()throws RemoteException;
    int getRuntimeServer()throws RemoteException;
    int getFileServer() throws RemoteException;
}
