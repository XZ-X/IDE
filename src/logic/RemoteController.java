package logic;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
/**
 * Created by xuxiangzhe on 2017/6/22.
 */
public class RemoteController {
    private static AccountI accountServer;
    private static IOProcessor ioProcessor;
    public static void connect(){
//        try {

//            ioProcessor=new IOProcessor();
//            LocateRegistry.createRegistry(5202);
//            Naming.bind("rmi://localhost:5202/ioProcessor",ioProcessor);
//            ioProcessor.putIn("fsw");
//            accountServer.startIO();
//        } catch (NotBoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//            System.out.println(accountServer.signUp("1","2","3","4"));

    }

    public static AccountI getAccountServer() {
        return accountServer;
    }

    public static void setAccountServer(AccountI accountServer) {
        RemoteController.accountServer = accountServer;
    }
}
