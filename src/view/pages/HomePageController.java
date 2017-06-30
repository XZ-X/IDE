package view.pages;

import Data.Language;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.remoteInterfaces.RemoteController;
import view.Begin.BFClient;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuxiangzhe on 2017/6/23.
 */
public class HomePageController implements Initializable {
    @FXML
    Button BFButton,OOKButton,openButton;
    @FXML
    TextField fileName;
    @FXML
    Label welcomeLabel,fileLabel,timeLabel;

    private boolean isValid=false;

    @FXML
    void createBFFile() throws IOException {
        if(fileName.getText().length()>=0&&isValid) {
            boolean isCreate = RemoteController.getFileServer().createFile(Language.BF, fileName.getText());
            if (isCreate) {
                EditPageController.fileName = fileName.getText();
                EditPageController.language = Language.BF;
                BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("editPage.fxml"))));
            }
        }else {
            fileLabel.setText("INVALID");
        }
    }

    @FXML
    void createOOKFile() throws IOException {
        if(fileName.getText().length()>=0&&isValid) {
            boolean isCreate = RemoteController.getFileServer().createFile(Language.OOK, fileName.getText());
            if (isCreate) {
                EditPageController.fileName = fileName.getText();
                EditPageController.language = Language.OOK;
                BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("editPage.fxml"))));
            }
        }else {
            fileLabel.setText("INVALID");
        }
    }

    @FXML
    void onOpenClicked() throws IOException {
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("openFile.fxml"))));
    }

    @FXML
    void logOut() throws IOException {
        RemoteController.getAccountServer().logOut(RemoteController.getUserName());
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("welcomePage.fxml"))));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeLabel.setText("Welcome\n"+RemoteController.getUserName());
        try {
            System.out.println(RemoteController.getFileServer().getLastLogIn());
            timeLabel.setText("Last login: "+RemoteController.getFileServer().getLastLogIn());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void checkFileName(){
        Matcher matcher= Pattern.compile("[^0-9a-zA-Z_]").matcher(fileName.getText());
        if(matcher.find()){
            fileLabel.setText("INVALID FILENAME!");
        }else {
            fileLabel.setText("");
            isValid=true;
        }
    }

}
