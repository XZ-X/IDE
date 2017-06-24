package view.pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import myTools.FileTools;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by xuxiangzhe on 2017/6/24.
 */
public class ExecuteController implements Initializable{
    public static String toExec;
    @FXML
    FlowPane contentPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.getChildren().clear();
        Button temp;
        for(char c:toExec.toCharArray()){
            temp=new Button(""+c);
            contentPane.getChildren().add(temp);
        }
    }
}
