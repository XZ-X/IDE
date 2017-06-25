package myTools;

import logic.remoteInterfaces.RemoteController;

import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/24.
 */
public class RunTools implements Runnable {
    String filename;
    String input;
    public RunTools(String filename,String input){
        this.filename=filename;
        this.input=input;
    }
    @Override
    public void run() {
        RemoteController.getIoProcessor().putIn(input);
        try {
            RemoteController.getRuntimeServer().setCurrentFile(filename);
            RemoteController.getRuntimeServer().run();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
