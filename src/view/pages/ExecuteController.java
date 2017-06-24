package view.pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import logic.remoteInterfaces.RemoteController;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by xuxiangzhe on 2017/6/24.
 */
public class ExecuteController implements Initializable{
    static String toExec;
    static String fileName;
    @FXML
    FlowPane contentPane;
    @FXML
    TextArea inputArea,outputArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.getChildren().clear();
        Button temp;
        for(char c:toExec.toCharArray()){
            temp=new Button(""+c);
            contentPane.getChildren().add(temp);
        }
        refreshOutput();
    }
    @FXML
    void refreshInput() throws RemoteException {
        RemoteController.getIoProcessor().putIn(inputArea.getText());
        RemoteController.getRuntimeServer().run();
        refreshOutput();
    }
    private void refreshOutput(){
        outputArea.setText(new String(RemoteController.getIoProcessor().getOutput()));
    }
}
