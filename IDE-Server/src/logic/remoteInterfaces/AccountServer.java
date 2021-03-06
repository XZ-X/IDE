package logic.remoteInterfaces;

import Data.GlobalConstant;
import Data.User;
import Data.UserState;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by xuxiangzhe on 2017/6/15.
 * This class will help users to login and get their own IOProcessors, RuntimeServers, etc.
 * All the users will share an accountServer so that a synchronized  keyword is added in the method
 * which will login the user and produce the other remote server to the user.
 *
 *
 */
public class AccountServer extends UnicastRemoteObject implements AccountI {
    private static ArrayList<IO> ioProcessors=new ArrayList<>();
    private static int IOUniqueNumber =0;
    private static int ClientID=0;
    private static User currentUser;
    private static int port;
    public AccountServer()throws RemoteException{
    }
    @Override
    synchronized public String login(String id, String password) throws RemoteException {
        User temp=User.login(id,password);
        switch (temp.getState()){
            case LogIn: {
                currentUser = temp;
                createOtherRemotes();
                return GlobalConstant.SUCCESS;

            }
            case UnknownUser:return GlobalConstant.LOGIN_FAIL_UNKNOWN;
            case DuplicateLogIn:return GlobalConstant.LOGIN_FAIL_DUP;
            case WrongPassword:return GlobalConstant.LOGIN_FAIL_WRONGPW;
        }

        return null;
    }
    //create other remotes for user who logs in successfully
    private void createOtherRemotes() throws RemoteException {
        ClientID++;
        FileServer fileServer=new FileServer(currentUser);
        RuntimeServer runtimeServer=new RuntimeServer(currentUser,ioProcessors.get(ioProcessors.size()-1));
        port=GlobalConstant.INITIAL_PORT+ClientID;
        LocateRegistry.createRegistry(port);

        try {
            Naming.bind("rmi://localhost:"+port+"/fileServer",fileServer);
            Naming.bind("rmi://localhost:"+port+"/runtimeServer",runtimeServer);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

    }
    @Override
    public String signUp(String id, String password, String secureQuestion, String answer) throws RemoteException {
        return User.signUp(id,password,secureQuestion,answer);
    }

    @Override
    public String logOut(String userName) throws RemoteException {
        User toLogOut=User.getUser(userName);
        toLogOut.logOut();
        return null;
    }

    @Override
    public String resetPassword(String username,String answer, String password) throws RemoteException {
        return User.getUser(username).changePassword(answer,password);
    }

    @Override
    public String forgetPassword(String username) throws RemoteException {
        User user=User.getUser(username);
        if(user.getState()!= UserState.UnknownUser){
            return user.getQuestions();
        }
        return GlobalConstant.LOGIN_FAIL_UNKNOWN;
    }


    //must be called immediately after the user logging in!
    @Override
    public void startIO() throws RemoteException {
        try {
            //to allocate unique IO processor to different user
            int temp=8126+ IOUniqueNumber;
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
