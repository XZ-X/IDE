package fxmls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

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
}
