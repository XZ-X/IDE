package view.pages;

import Data.GlobalConstant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logic.remoteInterfaces.RemoteController;
import view.Begin.BFClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @FXML
    AnchorPane panel;
    @FXML
    Button startButton;
    @FXML
    TextField userName;
    @FXML
    PasswordField password;

    @FXML
    //reset the notice to start coding after user typing new words
    void modified(){
        String normal = "Start Coding";
        startButton.setText(normal);
    }

    @FXML
    void signIn() throws IOException {
        String signUp = "Unknown User! Sign up now!";
        if(startButton.getText().equals(signUp)){
            BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("signUp.fxml"))));
        }else {
            String result= RemoteController.login(userName.getText(),password.getText());
            switch (result) {
                case GlobalConstant.LOGIN_FAIL_UNKNOWN:
                    startButton.setText(signUp);
                    break;
                case GlobalConstant.LOGIN_FAIL_DUP:
                    startButton.setText("You've logged in");
                    break;
                case GlobalConstant.LOGIN_FAIL_WRONGPW:
                    password.clear();
                    startButton.setText("Wrong password!");
                    break;
                case GlobalConstant.SUCCESS:
                    startButton.setText("Success!");
                    BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("homePage.fxml"))));
                    break;
            }
        }
    }

    @FXML
    void aboutPassword() throws IOException {
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("authorizationPage.fxml"))));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        panel.getStylesheets().add("view/resource/Configure.css");
    }
}
