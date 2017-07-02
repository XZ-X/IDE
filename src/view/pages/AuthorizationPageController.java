package view.pages;

import Data.GlobalConstant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.remoteInterfaces.RemoteController;
import view.Begin.BFClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationPageController {
    private String username;
    private boolean isValid=false;
    @FXML
    TextArea questionText;
    @FXML
    TextField IDText,answer,newPassword,newPasswordAgain;
    @FXML
    Button OKButton,getQuestionButton;


    @FXML
    //check users password and answer
    void onOKClicked() throws IOException {
        if(isValid) {
            if (username != null && newPassword.getText().equals(newPasswordAgain.getText())) {
                String password = newPassword.getText();
                Matcher letterChecker = Pattern.compile("[a-zA-Z]").matcher(password);
                Matcher numberChecker = Pattern.compile("[0-9]").matcher(password);
                Matcher otherChecker = Pattern.compile("[^0-9a-zA-Z]").matcher(password);
                if (password.length() < 8 || password.length() > 16) {
                    OKButton.setText("Control your length!");
                } else if (letterChecker.find() && numberChecker.find() && otherChecker.find()) {
                    String ret = RemoteController.getAccountServer().resetPassword(username, answer.getText(), newPassword.getText());
                    if (!ret.equals(GlobalConstant.SUCCESS)) {
                        OKButton.setText("Something wrong!");
                    } else {
                        if(!RemoteController.login(username,newPassword.getText()).equals(GlobalConstant.SUCCESS)){
                            RemoteController.getAccountServer().logOut(username);
                            RemoteController.login(username,newPassword.getText()).equals(GlobalConstant.SUCCESS);
                        }
                        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("homePage.fxml"))));
                    }
                } else {
                    OKButton.setText("Unsafe password!");
                }
            } else {
                OKButton.setText("Oops, you forget it quickly!");
            }
        }else {
            OKButton.setText("? Have you signed up?");
        }
    }

    @FXML
    //when user enter his ID, this method will fetch his security questions
    void setGetQuestionButtonClicked() throws RemoteException {
        username=IDText.getText();
        String ret= RemoteController.getAccountServer().forgetPassword(username);
        if(!ret.equals(GlobalConstant.LOGIN_FAIL_UNKNOWN)){
            isValid=true;
            questionText.setText(ret);
        }else {
            getQuestionButton.setText(ret);
            isValid=false;
        }
    }

    @FXML
    void clearButton(){
        OKButton.setText("OK");
        getQuestionButton.setText("Get your security question");
    }
    @FXML
    void changeUser(){
        clearButton();
        isValid=false;
    }
    @FXML
    void goBack() throws IOException {
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("welcomePage.fxml"))));
    }

}
