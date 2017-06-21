package view.Begin;

/**
 * Created by xuxiangzhe on 2017/6/15.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.CMain;

import java.io.IOException;

public class BFClient extends Application {
    public static Stage ps;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ps=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/pages/frontPage.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        CMain.main(null);
    }

}
