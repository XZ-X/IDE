package view.pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logic.RemoteController;
import myTools.FileTools;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.ResourceBundle;

/**
 * Created by xuxiangzhe on 2017/6/23.
 */
public class EditPageController implements Initializable {
    public static String fileName;
    @FXML
    TextField content;
    @FXML
    MenuItem save;

    @FXML
    void onSaveClicked() throws RemoteException {
        RemoteController.getFileServer().saveFile(content.getText());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            File file=RemoteController.getFileServer().openFile(fileName);
            content.setText(FileTools.convertF2S(file));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
