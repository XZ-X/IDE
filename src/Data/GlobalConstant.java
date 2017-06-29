package Data;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class GlobalConstant {
    //file paths
    public static final String USERS="userInformation";
    public static final String FILES="fileInformation";
    public static final String USER_FILES="IDE-Server/src/logic/files/";

    //file information
    public static final String FILE_NAME_SEPARATOR ="###";

    //initial port number
    public static final int INITIAL_PORT=5202;

    //sign up messages
    public static final String SUCCESS ="Success!";

    //sign in messages
    public static final String LOGIN_FAIL_UNKNOWN="Unknown user!";
    public static final String LOGIN_FAIL_DUP="Duplicated log in!";
    public static final String LOGIN_FAIL_WRONGPW="Wrong password!";
    public static final String LOGIN_SUCCESSFUL="Success!";

    //debug
    public static final int DEBUG_TIME_OUT=10;
    public static final String DEBUG_TIME_OUT_MESSAGE="TimeOut!";
    public static final String DEBUG_WRONG_MESSAGE="Wrong...";
    public static final String DEBUG_FINISH="finish";
    public static final ArrayList<Character> bfKeyWords= new ArrayList<>(Arrays.asList(',','.','<','>','[',']','+','-'));

    //syntax check expressions
    public static final String OOK_SYNTAX="([Oo]{2}[kK]([?!.])[Oo]{2}[kK]([?!.]))";
}
