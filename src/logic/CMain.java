package logic;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class CMain {
    private static AccountI accountServer=null;
    private static IOProcessor ioProcessor=null;
    public static void main(String[] args) {
        try {
            accountServer=(AccountI) Naming.lookup("rmi://localhost:6528/accountServer");
//            System.out.println(accountServer.signUp("1","2","3","4"));
            ioProcessor=new IOProcessor();
            LocateRegistry.createRegistry(5202);
            Naming.bind("rmi://localhost:5202/ioProcessor",ioProcessor);
            ioProcessor.putIn("fsw");
            accountServer.startIO();
            System.out.println(ioProcessor.putOut()[0]);
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
    public static String signUp(String userName,String password,String secureQuestion,String answer) throws RemoteException {
        return accountServer.signUp(userName,password,secureQuestion,answer);
    }
}
