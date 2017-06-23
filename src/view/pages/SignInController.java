package view.pages;

import Data.GlobalConstant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.CMain;
import logic.RemoteController;
import view.Begin.BFClient;

import java.io.IOException;

/**
 * Created by xuxiangzhe on 2017/6/22.
 */
public class SignInController {
    private String signUp="Unknown User!\n"+
            "Sign up now!";
    private String normal="Start Coding";
    @FXML
    Button button;
    @FXML
    TextField userName;
    @FXML
    PasswordField password;

    @FXML
    void modified(){
        button.setText(normal);
    }

    @FXML
    void signIn() throws IOException {
        if(button.getText().equals(signUp)){
            BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("signUp.fxml"))));
        }else {
            String result= RemoteController.login(userName.getText(),password.getText());
            switch (result) {
                case GlobalConstant.LOGIN_FAIL_UNKNOWN:
                    button.setText(signUp);
                    break;
                case GlobalConstant.LOGIN_FAIL_DUP:
                    button.setText("You've logged in");
                    break;
                case GlobalConstant.LOGIN_FAIL_WRONGPW:
                    password.clear();
                    button.setText("Wrong password!");
                    break;
                case GlobalConstant.LOGIN_SUCCESSFUL:
                    button.setText("Success!");
                    BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("createFile.fxml"))));
                    break;
            }
        }
    }
}
