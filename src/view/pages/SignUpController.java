package view.pages;

import Data.GlobalConstant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import logic.remoteInterfaces.RemoteController;
import view.Begin.BFClient;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpController {
    @FXML
    private Button goButton;
    @FXML
    private TextField username,questionText,answerText;
    @FXML
    private PasswordField passwordText;
    @FXML
    Label usernameCheckLabel,passwordCheckLabel;
    private boolean isUsernameValid =false,isPasswordValid=false;


    private void go() throws IOException, InterruptedException {
        if(isUsernameValid &&isPasswordValid) {
            String message = RemoteController.getAccountServer().signUp(username.getText(), passwordText.getText(), questionText.getText(), answerText.getText());
            if (message.equals(GlobalConstant.SUCCESS)) {
                goButton.setText(message);
                BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("homePage.fxml"))));
            } else {
                goButton.setText(message);
            }
        }
    }
    //shorter than 16 words, contains only a-zA-Z0-9
    @FXML
    void checkUsername(){
        isUsernameValid=false;
        usernameCheckLabel.setText("");
        String name=username.getText();
        if(name.length()>16){
            usernameCheckLabel.setText("Too long!");
        }else {
            Matcher checker= Pattern.compile("([^0-9a-zA-Z])").matcher(name);
            if(checker.find()){
                usernameCheckLabel.setText("Invalid ID!");
            }else {
                isUsernameValid =true;
            }
        }
    }
    //8-16 contains numbers, letters and others
    @FXML
    void checkPassword(){
        isPasswordValid=false;
        passwordCheckLabel.setText("");
        String password=passwordText.getText();
        if(password.length()<3){//TODO:to debug, modify later
            passwordCheckLabel.setText("Too short!");
        }else if(password.length()>16){
            passwordCheckLabel.setText("Too long!");
        }else {
            Matcher letterChecker=Pattern.compile("[a-zA-Z]").matcher(password);
            Matcher numberChecker=Pattern.compile("[0-9]").matcher(password);
            Matcher otherChecker=Pattern.compile("[^0-9a-zA-Z]").matcher(password);
            if(letterChecker.find()&&numberChecker.find()&&otherChecker.find()){
                isPasswordValid=true;
            }else {
                passwordCheckLabel.setText("Passwords must have\n" +
                        "numbers, letters\n" +
                        "and others");
            }
        }
    }




    @FXML
    void onGoClicked(){
        Alert confirm=new Alert(Alert.AlertType.NONE,"Are you sure? You can NOT change this latter",new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE),new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE));
        confirm.setTitle("Wait a minute!");
        confirm.showAndWait().ifPresent(buttonType -> {
            switch (buttonType.getButtonData()){
                case OK_DONE:
                    try {
                        go();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(".....");
                    }
                    break;
                default:
                    confirm.close();
            }
        });
    }

}
