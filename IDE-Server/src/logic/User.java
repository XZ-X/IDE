package logic;

import Data.GlobalConstant;
import Data.UserState;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public IO IOProcessor;
    private String password;
    private Clock clock;
    private Map<String,String> secureQuestions=new HashMap<>();
    private Settings settings=new Settings();
    private ArrayList<MyFile> files=new ArrayList<>();
    private User(String nm,String password){
        state=UserState.LogIn;
        name=nm;
        this.password =encrypt(password);
        users.add(this);
    }
    private User(String nm,String password,UserState userState){
        name=nm;
        this.password =encrypt(password);
        state=userState;
    }
    private static String encrypt(String src){
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            md5.update(src.getBytes());
            return new String(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "wrong!";
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
                while(true){
                    try {
                        users.add((User)inputStream.readObject());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        } catch (EOFException e){

        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println(users.toString());
        return true;
    }

    static String signUp(String userName,String password,String secureQuestion,String answer){
        for(User usr:users){
            if(usr.name.equals(userName)){
                System.out.println("error duplicated user");
                return "Duplicated user!";
            }
        }
        User user=new User(userName,password);
        user.state=UserState.Normal;
        user.secureQuestions.put(secureQuestion,answer);
        users.add(user);
        System.out.println("I creat a User!");
        storeUsers();
        return "Success!";
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
