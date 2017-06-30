package view.pages;

import Data.Language;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import logic.remoteInterfaces.RemoteController;
import clientUtilities.FileTools;
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
    static Language language;
    static String fileName;
    @FXML
    AnchorPane panel;
    @FXML
    TextArea content,input;
    @FXML
    MenuItem save,back,run;
    @FXML
    Label filename,wrongLabel;
    @FXML
    Button runButton;

    private boolean isValid=false;

    @FXML
    void goHome() throws IOException {
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("homePage.fxml"))));
    }

    @FXML
    void onSaveClicked() throws RemoteException {
        RemoteController.getFileServer().saveFile(content.getText());
    }
    @FXML
    void onRunClicked() throws IOException {
        if(isValid) {
            onSaveClicked();
            RemoteController.getIoProcessor().putIn(input.getText());
            try {
                RemoteController.getRuntimeServer().setCurrentFile(fileName);
                RemoteController.getRuntimeServer().run();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            ExecuteController.language = language;
            ExecuteController.fileName = fileName;
            ExecuteController.toExec = content.getText();
            BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("execute.fxml"))));
        }
    }

    @FXML
    void onLabelClicked(){
        TextField newName=new TextField();
        newName.setPrefWidth(filename.getPrefWidth());
        newName.setPrefHeight(filename.getPrefHeight());
        newName.setLayoutX(filename.getLayoutX());
        newName.setLayoutY(filename.getLayoutY());
        panel.getChildren().remove(filename);
        for(Node node:panel.getChildren()) {
            node.setOnMouseClicked(event -> {
                if (!(event.getSource() instanceof TextField)) {
                    try {
                        fileName = newName.getText();
                        RemoteController.getFileServer().renameFile(newName.getText());
                        panel.getChildren().remove(newName);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                ((Node)event.getSource()).setOnMouseClicked(null);
                filename.setText(fileName);
            });
        }
        panel.getChildren().add(filename);
        panel.getChildren().add(newName);
        newName.requestFocus();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            File file=RemoteController.getFileServer().openFile(fileName);
            switch (file.getName().split("\\.")[1]){
                case "bf":
                    language=Language.BF;
                    break;
                case "ook":
                    language=Language.OOK;
                    break;
            }
            content.setText(FileTools.convertF2S(file));
            filename.setText(fileName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void openFile() throws IOException {
        BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("openFile.fxml"))));
    }

    @FXML
    private void syntaxCheck(){
        switch (language){
            case BF:
                int numberOfBEQZ=content.getText().length()-content.getText().replaceAll("\\[","").length();
                int numberOfBNEZ=content.getText().length()-content.getText().replaceAll("]","").length();
                if(numberOfBEQZ!=numberOfBNEZ){
                    wrongLabel.setText("Wrong([/])");
                    isValid=false;
                }else {
                    isValid=true;
                    wrongLabel.setText("");
                }
                break;
            case OOK:
                String temp=content.getText().replaceAll(" ","");
                int numberOfOOKBEQZ=temp.length()-temp.replaceAll("[Oo]{2}[kK]([?])[Oo]{2}[kK]([!])","").length();
                int numberOfOOKBNEZ=temp.replaceAll("[Oo]{2}[kK]([?])[Oo]{2}[kK]([!])","").length()-temp.replaceAll("[Oo]{2}[kK]([?])[Oo]{2}[kK]([!])","").replaceAll("[Oo]{2}[kK]([!])[Oo]{2}[kK]([?])","").length();
                if(numberOfOOKBEQZ!=numberOfOOKBNEZ){
                    wrongLabel.setText("Wrong(!?/?!)");
                    isValid=false;
                }else {
                    isValid=true;
                    wrongLabel.setText("");
                }
                break;
        }
    }
}
