package logic.remoteInterfaces;

import Data.GlobalConstant;
import logic.User;
import logic.remoteInterfaces.AccountI;
import logic.remoteInterfaces.IO;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class AccountServer extends UnicastRemoteObject implements AccountI {
    public static ArrayList<IO> ioProcessors=new ArrayList<>();
    private static int IOUniqueNumber =0;
    private static int ClientID=0;
    private static User currentUser;
    private static int port;
    public AccountServer()throws RemoteException{
    }
    @Override
    public String login(String id, String password) throws RemoteException {
        User temp=User.login(id,password);
        switch (temp.getState()){
            case LogIn:{
                currentUser=temp;
                createOtherRemotes();
                return GlobalConstant.SIGNUP_SUCCESS;
            }
            case UnknownUser:return GlobalConstant.LOGIN_FAIL_UNKNOWN;
            case DuplicateLogIn:return GlobalConstant.LOGIN_FAIL_DUP;
            case WrongPassword:return GlobalConstant.LOGIN_FAIL_WRONGPW;
        }

        return null;
    }
    //create other remotes for user who logs in successfully
    private void createOtherRemotes() throws RemoteException {
        FileServer fileServer=new FileServer(currentUser);
        RuntimeServer runtimeServer=new RuntimeServer(currentUser);
        port=GlobalConstant.INITIAL_PORT+ClientID;
        ClientID++;

        try {
            Naming.bind("rmi://localhost:"+port+"/fileServer",fileServer);
            Naming.bind("rmi://localhost:"+port+"/runtimeServer",runtimeServer);
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public String signUp(String id, String password, String secureQuestion, String answer) throws RemoteException {
        String temp=User.signUp(id,password,secureQuestion,answer);
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
            //to allocate unique IO processor to different user
            int temp=5202+ IOUniqueNumber;
            IOUniqueNumber++;
            ioProcessors.add ((IO)Naming.lookup("rmi://localhost:"+temp+"/ioProcessor"));

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("here");
        System.out.println(ioProcessors.get(IOUniqueNumber-1).getInput());
    }

    @Override
    public int getRuntimeServer() throws RemoteException {
        return port;
    }

    @Override
    public int getFileServer() throws RemoteException {
        return port;
    }

    @Override
    public int getIOUniqueNumber() throws RemoteException {
        return IOUniqueNumber;
    }


}
