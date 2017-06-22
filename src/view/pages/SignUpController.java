package view.pages;

import Data.GlobalConstant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.CMain;
import logic.RemoteController;

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
        String message= RemoteController.getAccountServer().signUp(Username.getText(),passwordText.getText(),"HA","longFrog");
        if(message.equals(GlobalConstant.SIGNUP_SUCCESS)){
            goButton.setText(message);
            //TODO: jump to another place
        }else {
            goButton.setText(message);
        }
    }

}
