package view.pages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import logic.RemoteController;
import myTools.FileTools;
import view.Begin.BFClient;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by xuxiangzhe on 2017/6/23.
 */
public class EditPageController implements Initializable {
    public static String fileName;
    @FXML
    TextField content;
    @FXML
    MenuItem save,back,run;
    @FXML
    Label filename;


    @FXML
    void onSaveClicked() throws RemoteException {
        RemoteController.getFileServer().saveFile(content.getText());
    }
    @FXML
    void onRunClicked() throws IOException {
        onSaveClicked();
        ExecuteController.toExec=content.getText();
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("execute.fxml"))));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            File file=RemoteController.getFileServer().openFile(fileName);
            content.setText(FileTools.convertF2S(file));
            filename.setText(fileName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
