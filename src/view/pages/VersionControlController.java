package view.pages;

import Data.GlobalConstant;
import clientUtilities.FileTools;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import logic.remoteInterfaces.RemoteController;
import view.Begin.BFClient;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

public class VersionControlController implements Initializable{
//    private static final String COMMON_STYLE="-fx-alignment: center;-fx-border-color: #ff1000;";
//    private static final String SELECTED_STYLE="-fx-alignment: center;-fx-border-color:aqua;";
    private static final String CHANGED_STYLE ="-fx-alignment: center;-fx-background-color:deepskyblue;";
    @FXML
    VBox versionList;
    @FXML
    FlowPane version1,version2;
    @FXML
    Button home,selectButton;
    //a couple of maps contains file-UI & UI-file
    private Map<ToggleButton,File> fileList=new HashMap<>();
    private Map<File,ToggleButton> fileListR=new HashMap<>();

    //a map of file-time
    static Map<File,String> versionMap;

    //to realize version control
    private ArrayList<File> toCompare=new ArrayList<>(2);
    private IntegerProperty fileCounter=new SimpleIntegerProperty(0);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        home.setOnAction(event -> {
            try {
                BFClient.ps.setScene(new Scene(FXMLLoader.load(getClass().getResource("homePage.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        selectButton.setOnAction(event -> onSelectedClicked());
        fileCounter.addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue()>=2){
                onVersionControlClicked();
            }
        });

        fileListR.clear();
        fileList.clear();
        versionList.getChildren().clear();
        ArrayList<Map.Entry<File,String>> entries=new ArrayList<>(versionMap.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getValue));
        for(Map.Entry<File,String> entry:entries){
            //make a label with proper style and text
            File tempFile=entry.getKey();
            String editTime=entry.getValue();
            ToggleButton temp=new ToggleButton(tempFile.getName().split(GlobalConstant.FILE_NAME_SEPARATOR)[0]+"\n" +editTime);
//            temp.setStyle(COMMON_STYLE);
            temp.setOnMouseClicked(event -> {
                ToggleButton source=(ToggleButton) event.getSource();
                if(!toCompare.contains(fileList.get(source))) {
//                    source.setStyle(SELECTED_STYLE);
                    if(toCompare.size()==2) {
                        //automatically delete the first selected file if the file number is larger than 2.
                        fileListR.get(toCompare.remove(0)).setSelected(false);
                    }
                    toCompare.add(fileList.get(source));
                    fileCounter.setValue(fileCounter.get()+1);
                }else {
                    fileCounter.setValue(fileCounter.get()-1);
                    toCompare.remove(fileList.get(source));
                }
            });
            fileList.put(temp,tempFile);
            fileListR.put(tempFile,temp);
            versionList.getChildren().add(temp);
        }
    }
    //the specific details of version control
    private void onVersionControlClicked(){
        version1.getChildren().clear();
        version2.getChildren().clear();
        String aFile= FileTools.convertF2S(toCompare.get(0));
        String bFile=FileTools.convertF2S(toCompare.get(1));
        String smallFile,bigFile;
        String subString;
        ArrayList<Label> LLabel=new ArrayList<>(),SLabel=new ArrayList<>();
        boolean isALonger=false;
       int length,minLength,maxLength;

       //indexL is the substring index in the long file
       int recordLength=0,indexL=0,indexS=0;
       if(aFile.length()>bFile.length()){
           minLength=bFile.length();
           maxLength=aFile.length();
           smallFile=bFile;
           bigFile=aFile;
           isALonger=true;
       }else {
           minLength=aFile.length();
           maxLength=bFile.length();
           smallFile=aFile;
           bigFile=bFile;
       }

       //find the longest same substring
       for(length=0;length<=minLength;length++){
           for(int i=0;length+i<minLength;i++){
               subString=smallFile.substring(i,i+length);
               if(bigFile.contains(subString)){
                   if(length>recordLength){
                       recordLength=length;
                       indexL=bigFile.indexOf(subString);
                       indexS=i;
                   }
               }
           }
       }
       char[] LChars=bigFile.toCharArray(),SChars=smallFile.toCharArray();
        Label temp;
       for(int i=0;i<maxLength;i++){
           if(i>=indexL&&i<indexL+length){
               temp=new Label(LChars[i]+"");
           }else {
               temp=new Label(LChars[i]+"");
               temp.setStyle(CHANGED_STYLE);
           }
           LLabel.add(temp);

       }

       for(int i=0;i<minLength;i++){
           if(i>=indexS&&i<indexS+length){
               temp=new Label(SChars[i]+"");
           }else {
               temp=new Label(SChars[i]+"");
               temp.setStyle(CHANGED_STYLE);
           }
           SLabel.add(temp);
       }

       if(isALonger){
           version1.getChildren().addAll(LLabel);
           version2.getChildren().addAll(SLabel);
       }else {
           version1.getChildren().addAll(SLabel);
           version2.getChildren().addAll(LLabel);
       }

    }

    private void onSelectedClicked() {
        if(fileListR.size()!=0) {
            ArrayList<File> files = new ArrayList<>(fileListR.keySet());
            files.sort(Comparator.comparing(File::getName));
            File toKeep=files.get(0);
            try {
                RemoteController.getFileServer().recovery(toKeep.getName());
                versionMap=RemoteController.getFileServer().checkVersions(toKeep.getName().split(GlobalConstant.FILE_NAME_SEPARATOR)[0]);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        initialize(null,null);

    }
}
