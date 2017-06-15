package view.fxmls;

import javafx.fxml.FXML;
import javafx.scene.shape.Ellipse;

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
