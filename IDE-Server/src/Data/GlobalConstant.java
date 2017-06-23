package Data;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class GlobalConstant {
    //file paths
    public static final String USERS="userInformation";
    public static final String FILES="fileInformation";
    public static final String USER_FILES="IDE-Server/src/logic/files/";

    //initial port number
    public static final int INITIAL_PORT=5202;

    //sign up messages
    public static final String SIGNUP_SUCCESS="Success!";

    //sign in messages
    public static final String LOGIN_FAIL_UNKNOWN="Unknown user!";
    public static final String LOGIN_FAIL_DUP="Duplicated log in!";
    public static final String LOGIN_FAIL_WRONGPW="Wrong password!";
    public static final String LOGIN_SUCCESSFUL="Success!";
}
