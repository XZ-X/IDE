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
    //local methods
    public void putIn(String input){
        if(this.input!=null) {
            this.input = (new String(this.input) + input).toCharArray();
        }else {
            this.input=input.toCharArray();
        }
    }
    public char[] getOutput(){
        if(output!=null) {
            return output;
        }else {
            return new char[]{(char)0};
        }
    }
    public char[] clearOutput(){
        char[] ret=getOutput();
        output=new char[]{(char)0};
        return ret;
    }

    //Methods prepared for server
    @Override
    public char getInput() throws RemoteException {
        if(input!=null&&input.length>0) {
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


}
