package logic.remoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The remote interface related to account
 */
@SuppressWarnings("unused")
public interface AccountI extends Remote {
    String login(String id, String passwd) throws RemoteException;
    String signUp(String id, String passwd, String secureQuestion, String answer)throws RemoteException;
    String logOut(String userName) throws RemoteException;
    String forgetPassword(String username)throws RemoteException;
    String resetPassword(String username,String answer, String password)throws RemoteException;
    void startIO()throws RemoteException;
    int getRuntimeServer()throws RemoteException;
    int getFileServer() throws RemoteException;
    int getIOUniqueNumber()throws RemoteException;
}
