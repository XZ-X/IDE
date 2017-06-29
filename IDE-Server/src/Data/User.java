package Data;

import logic.Settings;
import logic.remoteInterfaces.IO;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuxiangzhe on 2017/6/15.Class User
 * compared with diagram: ++storeUsers() ++loadUsers()
 * Unfinished
 */
public class User implements Serializable,Runnable {
    //All the effective users will appear in this list, which uses loadUser() and storeUser() to edit.
    private static Set<User> users = new HashSet<>();
    //state
    private UserState state;
    //for concurrency
    private boolean isRun=true;
    private transient Thread thread=new Thread(this);

    //helper
    public IO IOProcessor;
    static private Clock clock=Clock.systemDefaultZone();
    private String time;

    //attribute
    public final String name;
    private String password;
    private Map<String, String> secureQuestions ;
    private Settings settings ;
    private ArrayList<MyFile> files;

    //constructors
    private User(String nm, String password) {
        state = UserState.LogIn;
        name = nm;
        this.password = encrypt(password);
        users.add(this);
        files=new ArrayList<>();
        System.out.println("here!!!!");
        secureQuestions= new HashMap<>();
        settings=new Settings();
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

    public String getLastLogin() {
        return time;
    }

    public Settings getPreference() {
        return settings;
    }

    public String getQuestions() {
        return new ArrayList<>(secureQuestions.keySet()).get(0);
    }

    public void setPreference(boolean isAutoSave, int autoSaveTime, int versionNumber) {
        settings.autoSaveTime = autoSaveTime;
        settings.isAutoSave = isAutoSave;
        settings.versionNumber = versionNumber;
    }

    public String getTime() {
        return time;
    }

    public static User getUser(String userName){
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
                    User x=(User) inputStream.readObject();
                    x.state=UserState.Normal;
                    users.add(x);
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
        user.secureQuestions.put(secureQuestion, encrypt(answer));
        users.add(user);
        System.out.println("I create a User!");
        storeUsers();
        return GlobalConstant.SUCCESS;
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
        if(secureQuestions.containsValue(encrypt(answer))){
            password=encrypt(newPasswd);
            return GlobalConstant.SUCCESS;
        }
        return GlobalConstant.LOGIN_FAIL_WRONGPW;
    }



    //user's functions
    public ArrayList<MyFile> getFile() {
        return files;
    }

    public MyFile getFile(String fileName){
        for(MyFile file:files){
            if(file.getName().equals(fileName)){
                return file;
            }
        }
        return null;
    }

    public void addFile(MyFile file) {
        files.add(file);
    }

    public void deleteFile(String filename) throws InterruptedException {
        MyFile toDelete=null;
        isRun=false;
        if(thread.getState()== Thread.State.TERMINATED) {
            for (MyFile file : files) {
                if (file.getName().equals(filename)) {
                    toDelete=file;
                }
            }
            if(toDelete!=null) {
                files.remove(toDelete);
                toDelete.delete();
            }
        }
        isRun=true;
        restart();
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
        while (isRun) {
            storeUsers();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void restart(){
        if(thread.getState()== Thread.State.TERMINATED) {
            thread = new Thread(this);
            thread.start();
        }
    }
}