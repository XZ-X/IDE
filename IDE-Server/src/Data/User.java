package Data;


import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuxiangzhe on 2017/6/15.
 *
 * This class is the model of "User" in this project.
 * Each user has a list of MyFiles which provides easy implement of delete file, add file and access control.
 *
 * The user-class also have a thread to save user's information such as files passwords, etc.
 * To make this server more efficient, the save thread will run every 10 seconds instead of run all the time.
 * User's password and answer to the sec-question is encrypted by the MD5 algorithm.
 *
 * compared with diagram: ++storeUsers() ++loadUsers()
 */
public class User implements Serializable,Runnable {
    //All the effective users will appear in this list, which uses loadUser() and storeUser() to edit.
    private static Set<User> users = new HashSet<>();
    //state
    private UserState state;
    //for concurrency
    private boolean isRun=true;
    private static transient Thread thread;//***this thread is transient which means that it will be null after reading from a file.

    //helper
    static private Clock clock=Clock.systemUTC();
    private String time;

    //attribute
    public final String name;
    private String password;
    private Map<String, String> secureQuestions ;
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

    public String getQuestions() {
        return new ArrayList<>(secureQuestions.keySet()).get(0);
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
            //in fact, this is the normal case and is the only normal way to jump out of the dead-loop above.
            e.printStackTrace();
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
            return user;
        }
    }

    public void logOut() {
        state = UserState.Normal;
        time=clock.instant().atOffset(ZoneOffset.ofHours(8)).toString();
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

    public boolean deleteFile(String filename) throws InterruptedException {
        MyFile toDelete=null;
        boolean ret=false;
        isRun=false;
        if(thread==null||thread.getState()!=Thread.State.RUNNABLE) {
            for (MyFile file : files) {
                if (file.getName().equals(filename)) {
                    toDelete=file;
                }
            }
            if(toDelete!=null) {
                files.remove(toDelete);
                ret=toDelete.delete();
            }
        }
        isRun=true;
        restart();
        return ret;
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

    @Override
    //auto-save
    public void run() {
        while (isRun) {
            System.out.println("here");
            storeUsers();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //restart the save thread.
    private void restart(){
        if(thread==null||thread.getState()== Thread.State.TERMINATED) {
            thread = new Thread(this);
            thread.start();
        }
    }
}