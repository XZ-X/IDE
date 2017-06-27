package view.pages;

import Data.GlobalConstant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.remoteInterfaces.RemoteController;
import view.Begin.BFClient;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * Created by xuxiangzhe on 2017/6/26.
 */
public class OpenFileController implements Initializable{
    @FXML
    Button vcButton,openButton;
    @FXML
    VBox fileList;
    private Map<Label,File> fileListMap=new LinkedHashMap<>();
    private File selectedFile;

    @FXML
    void onOpenClicked() throws IOException {
        EditPageController.fileName=selectedFile.getName().split(GlobalConstant.FILE_NAME_SEPERATOR)[0];
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("editPage.fxml"))));
    }

    @FXML
    void onVCClicked() throws IOException {
        VersionControlController.versionMap=RemoteController.getFileServer().checkVersions(selectedFile.getName().split(GlobalConstant.FILE_NAME_SEPERATOR)[0]);
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("versionControl.fxml"))));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            File[] files= RemoteController.getFileServer().lookupFile();
            for(File file : files){
                String name=file.getName().split(GlobalConstant.FILE_NAME_SEPERATOR)[0];
                Label temp=new Label(name);
                temp.setStyle("-fx-alignment: center;");
                temp.setOnMouseClicked(event -> selectedFile=fileListMap.get(event.getSource()));
                fileList.getChildren().add(temp);
                fileListMap.put(temp,file);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
