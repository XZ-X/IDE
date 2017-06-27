package logic;

import Data.User;
import logic.remoteInterfaces.AccountServer;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class SMain {
    public static void main(String[] args) {
        try {
            AccountServer accountServer=new AccountServer();
            LocateRegistry.createRegistry(6528);
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
        User.loadUsers();
        Thread storeUsers=new Thread(new User());
        storeUsers.start();
    }
}
