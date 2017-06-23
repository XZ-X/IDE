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
        RemoteController.connect();
        try {
            accountServer=(AccountI) Naming.lookup("rmi://localhost:6528/accountServer");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
