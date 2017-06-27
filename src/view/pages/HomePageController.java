package view.pages;

import Data.Language;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.remoteInterfaces.RemoteController;
import view.Begin.BFClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by xuxiangzhe on 2017/6/23.
 */
public class HomePageController implements Initializable {
    @FXML
    Button BFButton,OOKButton,openButton;
    @FXML
    TextField fileName;

    @FXML
    void createBFFile() throws IOException {
        boolean isCreate=RemoteController.getFileServer().createFile(Language.BF,fileName.getText());
        if(isCreate){
            EditPageController.fileName =fileName.getText();
            BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("editPage.fxml"))));
        }
    }

    @FXML
    void createOOKFile(){

    }

    @FXML
    void onOpenClicked() throws IOException {
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("openFile.fxml"))));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
