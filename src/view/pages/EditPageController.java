package view.pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logic.RemoteController;
import myTools.FileTools;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by xuxiangzhe on 2017/6/23.
 */
public class EditPageController implements Initializable {
    public static String filename;
    @FXML
    TextField content;
    @FXML
    MenuItem save,back,run;
    @FXML
    Label fileName;


    @FXML
    void onSaveClicked() throws RemoteException {
        RemoteController.getFileServer().saveFile(content.getText());
    }
    @FXML
    void onRunClicked() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            File file=RemoteController.getFileServer().openFile(filename);
            content.setText(FileTools.convertF2S(file));
            fileName.setText(filename);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
