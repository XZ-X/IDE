package view.pages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.shape.Ellipse;
import view.Begin.BFClient;

import java.io.IOException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class LoginController {
    @FXML
    Ellipse loginColor;
    @FXML
    Ellipse signUpColor;
    @FXML
    void coloredButton(){
        loginColor.setVisible(true);
    }
    @FXML
    void uncoloredButton(){
        loginColor.setVisible(false);
    }
    @FXML
    void coloredButton2(){
        signUpColor.setVisible(true);
    }

    @FXML
    void uncoloredButton2(){
        signUpColor.setVisible(false);
    }

    @FXML
    void signUpClicked(){
        try {
            BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/pages/signUp.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
