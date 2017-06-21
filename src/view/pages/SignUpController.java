package view.pages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.CMain;

import java.rmi.RemoteException;

/**
 * Created by xuxiangzhe on 2017/6/21.
 */
public class SignUpController {
    @FXML
    private Button goButton;
    @FXML
    private TextField Username;
    @FXML
    private PasswordField passwordText;


    @FXML
    private void go() throws RemoteException {
        CMain.signUp(Username.getText(),passwordText.getText(),"HA","longFrog");
    }

}
