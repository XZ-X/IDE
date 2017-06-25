package view.pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import logic.remoteInterfaces.RemoteController;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by xuxiangzhe on 2017/6/24.
 */
public class ExecuteController implements Initializable{
    static String toExec;
    static String fileName;
    private Map<Button,Integer> breakpointMap =new HashMap<>();
    @FXML
    FlowPane contentPane;
    @FXML
    TextArea inputArea,outputArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.getChildren().clear();
        Button temp;
        int cnt=0;
        for(char c:toExec.toCharArray()){
            temp=new Button(""+c);
            breakpointMap.put(temp,cnt++);
            temp.setOnMouseClicked(event -> {

            });
            contentPane.getChildren().add(temp);
        }
        appendRefreshOutput();
    }
    @FXML
    void refreshOutput(){
        appendRefreshOutput();
        RemoteController.getIoProcessor().clearOutput();
    }

    @FXML
    void refreshInput() throws RemoteException {
        RemoteController.getIoProcessor().putIn(inputArea.getText());
        RemoteController.getRuntimeServer().run();
        refreshOutput();
    }

    @FXML
    void terminate() throws RemoteException {
        RemoteController.getRuntimeServer().terminate();
    }
    private void setBreakpoint(){

    }

    private void cancelBreakpoint(){

    }



    private void appendRefreshOutput(){
        outputArea.setText(new String(RemoteController.getIoProcessor().getOutput()));
    }
}
