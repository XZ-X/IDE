package logic;

import Data.GlobalConstant;
import Data.MyFile;
import Data.UserState;
import logic.remoteInterfaces.IO;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuxiangzhe on 2017/6/15.Class User
 * compared with diagram: ++storeUsers() ++loadUsers()
 * Unfinished
 */
public class User implements Serializable,Runnable {
    //All the effective users will appear in this list, which uses loadUser() and storeUser() to edit.
    private static ArrayList<User> users = new ArrayList<>();
    //state
    private UserState state;

    //helper
    public IO IOProcessor;
    static private Clock clock=Clock.systemUTC();
    private String time;

    //attribute
    public final String name;
    private String password;
    private Map<String, String> secureQuestions = new HashMap<>();
    private Settings settings = new Settings();
    private ArrayList<MyFile> files = new ArrayList<>();

    //constructors
    private User(String nm, String password) {
        state = UserState.LogIn;
        name = nm;
        this.password = encrypt(password);
        users.add(this);
    }

    //This constructor is used to build users when there're some problems in method 'logIn',such as unknownUser.
    //Users built by this method will NOT be added into the user list since they're merely used to convey error information
    private User(UserState userState) {
        name="wrong!";
        password="wrong!";
        state = userState;
    }
    //This constructor is used to build a user aiming at use the auto-store method.
    public User(){
        name="";
    }

    //gets-sets methods
    public UserState getState() {
        return state;
    }

    public Clock getLastLogin() {
        return clock;
    }

    public Settings getPreferrence() {
        return settings;
    }

    public String[] getQuestions() {
        return (String[]) secureQuestions.keySet().toArray();
    }

    public void setPreferrence(boolean isAutoSave, int autoSaveTime, int versionNumber) {
        settings.autoSaveTime = autoSaveTime;
        settings.isAutoSave = isAutoSave;
        settings.versionNumber = versionNumber;
    }

    public String getTime() {
        return time;
    }

    public User getUser(String userName){
        for(User user:users){
            if (user.name.equals(userName)){
                return user;
            }
        }
        return new User(UserState.UnknownUser);
    }

    //methods used to manage users
    public static boolean storeUsers() {
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(GlobalConstant.USERS));
            for (User usr : users) {
                stream.writeObject(usr);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean loadUsers() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(GlobalConstant.USERS));
            while (true) {
                try {
                    users.add((User) inputStream.readObject());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (EOFException e) {

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println(users.toString());
        return true;
    }

    public static String signUp(String userName, String password, String secureQuestion, String answer) {
        for (User usr : users) {
            if (usr.name.equals(userName)) {
                System.out.println("error duplicated user");
                return "Duplicated user!";
            }
        }
        User user = new User(userName, password);
        user.state = UserState.Normal;
        user.secureQuestions.put(secureQuestion, answer);
        users.add(user);
        System.out.println("I creat a User!");
        storeUsers();
        return GlobalConstant.SIGNUP_SUCCESS;
    }

    public static User login(String userName, String password) {
        User user = null;
        for (User person : users) {
            if (person.name.equals(userName)) {
                user = person;
                break;
            }
        }
        if (user == null) {
            return new User(UserState.UnknownUser);
        }else if(!user.password.equals(encrypt(password))){
            return new User(UserState.WrongPassword);
        }
        //Avoid duplicated logging in
        if(user.getState()==UserState.LogIn){
            return new User(UserState.DuplicateLogIn);
        }else {
            user.state=UserState.LogIn;
            user.time=clock.toString();
            return user;
        }
    }

    public void logOut() {
        state = UserState.Normal;
        //TODO:
    }

    public String changePassword(String answer, String newPasswd) {
        //TODO:
        return null;
    }



    //user's functions
    public ArrayList<MyFile> watchFile() {
        return files;
    }

    public void addFile(MyFile file) {
        files.add(file);
    }

    public void deleteFile(String filename) {
        for (MyFile file : files) {
            //TODO:
        }
    }



    //utilities
    private static String encrypt(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(src.getBytes());
            return new String(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "wrong!";
    }
    //auto-save
    @Override
    public void run() {
        storeUsers();
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}