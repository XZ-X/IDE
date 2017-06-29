package view.pages;

import Data.GlobalConstant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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



public class OpenFileController implements Initializable{
    @FXML
    Button vcButton,openButton,deleteButton;
    @FXML
    VBox fileList;
    private Map<Button,File> fileListMap=new LinkedHashMap<>();
    private File selectedFile;
    private final String DEFAULT="-fx-background-color: #d8d6da ;",SELECTED="-fx-background-color: lightslategrey;";

    @FXML
    void onOpenClicked() throws IOException {
        EditPageController.fileName=selectedFile.getName().split(GlobalConstant.FILE_NAME_SEPARATOR)[0];
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("editPage.fxml"))));
    }

    @FXML
    void onVCClicked() throws IOException {
        VersionControlController.versionMap=RemoteController.getFileServer().checkVersions(selectedFile.getName().split(GlobalConstant.FILE_NAME_SEPARATOR)[0]);
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("versionControl.fxml"))));
    }

    @FXML
    void onDeleteClicked(){
        Alert confirm=new Alert(Alert.AlertType.NONE,"Think twice before you delete!"
                ,new ButtonType("Delete anyway", ButtonBar.ButtonData.OK_DONE)
                ,new ButtonType("Let me think....", ButtonBar.ButtonData.CANCEL_CLOSE));
        confirm.showAndWait().ifPresent(buttonType -> {
            switch (buttonType.getButtonData()){
                case OK_DONE:
                    try {
                        RemoteController.getFileServer().deleteFile(selectedFile.getName().split(GlobalConstant.FILE_NAME_SEPARATOR)[0]);
                        initialize(null,null);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileList.getChildren().clear();
        fileListMap.clear();
        try {
            File[] files= RemoteController.getFileServer().lookupFile();
            selectedFile=files[0];
            for(File file : files){
                String name=file.getName().split(GlobalConstant.FILE_NAME_SEPARATOR)[0];
                Button temp=new Button(name);
                temp.setOnMouseClicked(event -> {
//                    if(selectedFile==fileListMap.get(event.getSource())){
//                        temp.setStyle(temp.getStyle().replace(SELECTED,"")+DEFAULT);
//                    }else {
//                        temp.setStyle(temp.getStyle().replace(DEFAULT,"")+SELECTED);
//                    }
                    selectedFile=fileListMap.get(event.getSource());

                });
                fileList.getChildren().add(temp);
                fileListMap.put(temp,file);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
