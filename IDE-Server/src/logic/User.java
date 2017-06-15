package logic;

import Data.GlobalConstant;
import Data.UserState;

import java.io.*;
import java.net.URL;
import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuxiangzhe on 2017/6/15.Class User
 * compared with diagram: ++storeUsers() ++loadUsers()
 * Unfinished
 */
public class User implements Serializable{
    private static ArrayList<User> users=new ArrayList<>();
    private   UserState state;
    public final String name;
    private String password;
    private Clock clock;
    private Map<String,String> secureQuestions=new HashMap<>();
    private Settings settings=new Settings();
    private ArrayList<MyFile> files=new ArrayList<>();

    private User(String nm,String passwd){
        state=UserState.LogIn;
        name=nm;
        password=passwd;
        users.add(this);
    }
    private User(String nm,String passwd,UserState userState){
        name=nm;
        password=passwd;
        state=userState;
    }
    static boolean storeUsers(){
        try {
            ObjectOutputStream stream=new ObjectOutputStream(new FileOutputStream(GlobalConstant.USERS));
            for (User usr:users) {
                stream.writeObject(usr);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static boolean loadUsers(){
        try {
            ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(GlobalConstant.USERS));
            try {
                while(true){
                    try {
                        users.add((User)inputStream.readObject());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }catch (OptionalDataException e){
                if(!e.eof){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static String signUp(String userName,String password,String secureQuestion,String answer){
        //TODO:
        return null;
    }

    static User login(String userName,String password) {
        //TODO:
        return null;
    }

    public UserState getState(){
        return state;
    }
    public Clock getLastLogin(){
        return clock;
    }
    public Settings getPreferrence(){
        return settings;
    }
    public String[] getQuestions(){
        return (String[])secureQuestions.keySet().toArray();
    }
    public void setPreferrence(boolean isAutoSave,int autoSaveTime,int versionNumber){
        settings.autoSaveTime=autoSaveTime;
        settings.isAutoSave=isAutoSave;
        settings.versionNumber=versionNumber;
    }
    public void logOut(){
        state=UserState.Normal;
        //TODO:
    }

    public String changePassword(String answer,String newPasswd){
        //TODO:
        return null;
    }
    public ArrayList<MyFile> watchFile(){
        return files;
    }
    public void addFile(MyFile file){
        files.add(file);
    }
    public void deleteFile(String filename){
        for(MyFile file:files){
            //TODO:
        }
    }
}
