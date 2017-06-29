package logic.remoteInterfaces;

import Data.GlobalConstant;
import logic.remoteInterfaces.AccountI;
import logic.remoteInterfaces.IOProcessor;
import logic.remoteInterfaces.MyFileI;
import logic.remoteInterfaces.RuntimeI;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
/**
 * This class gives more supports to the client-server model.
 */
public class RemoteController {
    private static AccountI accountServer;
    private static IOProcessor ioProcessor;
    private static MyFileI fileServer;
    private static RuntimeI runtimeServer;
    private static String userName;
    //gets
    public static AccountI getAccountServer() {
        return accountServer;
    }

    public static MyFileI getFileServer() {
        return fileServer;
    }

    public static IOProcessor getIoProcessor() {
        return ioProcessor;
    }

    public static RuntimeI getRuntimeServer() {
        return runtimeServer;
    }

    public static String getUserName() {
        return userName;
    }

    //connect to server
    public static void connect(){
        try {
            accountServer=(AccountI) Naming.lookup("rmi://localhost:6528/accountServer");
            //to allocate different IO processors to different users
            int uniqueNumber = accountServer.getIOUniqueNumber();
            ioProcessor=new IOProcessor();
            int temp=8126+ uniqueNumber;
            LocateRegistry.createRegistry(temp);
            Naming.bind("rmi://localhost:"+temp+"/ioProcessor",ioProcessor);
            accountServer.startIO();

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    //login **this means an unique fileServer and an unique runtimeServer will be allocated to this user**
    public static String login(String id,String password) throws RemoteException {
        String ret=accountServer.login(id,password);
        if(ret.equals(GlobalConstant.SUCCESS)){
            userName=id;
            int port=accountServer.getFileServer();
            try {
                fileServer=(MyFileI)Naming.lookup("rmi://localhost:"+port+"/fileServer");
                runtimeServer=(RuntimeI)Naming.lookup("rmi://localhost:"+port+"/runtimeServer");
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }


}
