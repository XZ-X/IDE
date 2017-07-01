package view.pages;

import view.Begin.BFClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;


public class WelcomePageController {
    @FXML
    void login() throws IOException {
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("user.fxml"))));
    }
}
