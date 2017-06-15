package logic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class AccountServer extends UnicastRemoteObject implements AccountI {
    public AccountServer()throws RemoteException{

    }
    @Override
    public String login(String id, String passwd) throws RemoteException {
        return null;
    }

    @Override
    public String signUp(String id, String passwd, String secureQuestion, String answer) throws RemoteException {
        return null;
    }

    @Override
    public String logOut() throws RemoteException {
        return null;
    }

    @Override
    public String resetPassword(String answer, String password) throws RemoteException {
        return null;
    }

    @Override
    public String forgetPassword() throws RemoteException {
        return null;
    }

    @Override
    public boolean setPreferences(int versionNumber, boolean isAutoSave, int autoSaveTime) throws RemoteException {
        return false;
    }
}
