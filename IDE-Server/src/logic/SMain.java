package logic;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class SMain {
    public static void main(String[] args) {
        try {
            User.loadUsers();
            AccountServer accountServer=new AccountServer();
            LocateRegistry.createRegistry(6528);
            LocateRegistry.createRegistry(8089);
            try {
                Naming.bind("rmi://localhost:6528/accountServer",accountServer);

            } catch (AlreadyBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}