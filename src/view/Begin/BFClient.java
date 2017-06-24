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
import logic.remoteInterfaces.RemoteController;

import java.io.IOException;

public class BFClient extends Application {
    public static Stage ps;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ps=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/pages/welcomePage.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event ->{
            try {
                RemoteController.getAccountServer().logOut(RemoteController.getUserName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        CMain.main(null);
    }

}
