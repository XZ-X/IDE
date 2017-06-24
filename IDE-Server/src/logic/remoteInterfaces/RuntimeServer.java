package logic.remoteInterfaces;

import Data.MyFile;
import logic.BFInterpreter;
import logic.Processor;
import logic.User;
import logic.remoteInterfaces.RuntimeI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by xuxiangzhe on 2017/6/15.
 * This class will exec user's code.
 */
public class RuntimeServer extends UnicastRemoteObject implements RuntimeI {
    private MyFile currentFile;
    private User usr;
    private IO io;
    private Processor processor;
    public RuntimeServer(User user,IO io)throws RemoteException{
        usr=user;
        this.io=io;
    }

    public void setCurrentFile(String filename) {
        currentFile=usr.getFile(filename);
    }

    @Override
    public void run() throws RemoteException {
        switch (currentFile.getType()){
            case BF:
                processor=new Processor(new BFInterpreter(io,currentFile));
                break;
            case OOK:
                //TODO:ook Interpreter
        }
        processor.exec();
    }

    @Override
    public String[] debug() throws RemoteException {
        return null;
    }

    @Override
    public void debug_breakpoint(int location) throws RemoteException {

    }

    @Override
    public String[] debug_back() throws RemoteException {
        return null;
    }

    @Override
    public String[] debug_next() throws RemoteException {
        return null;
    }
}
