package logic;

import Data.GlobalConstant;
import logic.remoteIneterfaces.AccountI;
import logic.remoteIneterfaces.IO;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class AccountServer extends UnicastRemoteObject implements AccountI {
    public IO ioProcessor;
    public AccountServer()throws RemoteException{

    }
    @Override
    public String login(String id, String passwd) throws RemoteException {
        User temp=User.login(id,passwd);
        switch (temp.getState()){
            case LogIn:return GlobalConstant.SIGNUP_SUCCESS;
            case UnknownUser:return GlobalConstant.LOGIN_FAIL_UNKNOWN;
            case DuplicateLogIn:return GlobalConstant.LOGIN_FAIL_DUP;
            case WrongPassword:return GlobalConstant.LOGIN_FAIL_WRONGPW;
        }
        return null;
    }

    @Override
    public String signUp(String id, String passwd, String secureQuestion, String answer) throws RemoteException {
        String temp=User.signUp(id,passwd,secureQuestion,answer);
        return temp;
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

    @Override
    public void startIO() throws RemoteException {
        try {
            ioProcessor = (IO) Naming.lookup("rmi://localhost:5202/ioProcessor");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("here");
        System.out.println(ioProcessor.getInput());
        ioProcessor.outPut('x');
        ioProcessor.outPut('x');
        ioProcessor.outPut('z');
//        ioProcessor.output('x');
//        ioProcessor.output('x');
//        ioProcessor.output('z');
    }

    @Override
    public int getRuntimeServer() throws RemoteException {
        return 0;
    }

    @Override
    public int getFileServer() throws RemoteException {
        return 0;
    }


}
