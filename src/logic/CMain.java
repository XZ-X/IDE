package logic;

import logic.remoteInterfaces.AccountI;
import logic.remoteInterfaces.IOProcessor;
import logic.remoteInterfaces.RemoteController;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class CMain {
    private static AccountI accountServer=null;
    private static IOProcessor ioProcessor=null;
    public static void main(String[] args) {
        RemoteController.connect();
    }

}
