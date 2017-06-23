package logic.remoteInterfaces;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 * Created by xuxiangzhe on 2017/6/16.
 */
public class IOProcessor extends UnicastRemoteObject implements IO {
    private char[] input;
    private char[] output;

    public IOProcessor() throws RemoteException {
    }

    public void putIn(String input){
        if(this.input!=null) {
            this.input = (new String(this.input) + input).toCharArray();
        }else {
            this.input=input.toCharArray();
        }
    }
    @Override
    public char getInput() throws RemoteException {
        if(input.length>0) {
            char c = input[0];
            input = new String(input).substring(1).toCharArray();
            return c;
        }else {
            return (char)-1;
        }
    }

    @Override
    public boolean outPut(char c) throws RemoteException {
        if(output!=null) {
            output = (new String(output) + c).toCharArray();
        }else {
            output=new char[]{c};
        }
        return true;
    }
    public char[] putOut(){
        if(output!=null) {
            return output;
        }else {
            return new char[]{(char)0};
        }
    }

}
