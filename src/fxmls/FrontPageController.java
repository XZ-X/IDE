package fxmls;

import Begin.BFClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */
public class FrontPageController {
    @FXML
    void login() throws IOException {
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("login.fxml"))));
    }
}
