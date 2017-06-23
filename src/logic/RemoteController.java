package logic;

import logic.remoteInterfaces.AccountI;
import logic.remoteInterfaces.IOProcessor;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
/**
 * Created by xuxiangzhe on 2017/6/22.
 */
public class RemoteController {
    private static AccountI accountServer;
    private static IOProcessor ioProcessor;
    private static int uniqueNumber;
    public static void connect(){
        try {
            accountServer=(AccountI) Naming.lookup("rmi://127.0.0.1:6528/accountServer");
            //to allocate different IO processors to different users
            uniqueNumber=accountServer.getIOUniqueNumber();
            ioProcessor=new IOProcessor();
            int temp=5202+uniqueNumber;
            LocateRegistry.createRegistry(temp);
            Naming.bind("rmi://localhost:"+temp+"/ioProcessor",ioProcessor);
            ioProcessor.putIn("fsw");
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

    public static AccountI getAccountServer() {
        return accountServer;
    }

}
