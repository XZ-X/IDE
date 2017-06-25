package view.pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import logic.remoteInterfaces.RemoteController;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by xuxiangzhe on 2017/6/24.
 */
public class ExecuteController implements Initializable{
    static String toExec;
    static String fileName;
    private Map<Button,Integer> contentsMap =new HashMap<>();
    private Map<Label,Integer> stackMap=new HashMap<>();
    private LinkedList<Integer> breakpoints=new LinkedList<>();

    @FXML
    FlowPane contentPane,stackPane;
    @FXML
    TextArea inputArea,outputArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.getChildren().clear();
        Button temp;
        int cnt=0;
        for(char c:toExec.toCharArray()){
            temp=new Button(""+c);
            temp.setStyle("-fx-background-color: whitesmoke");
            contentsMap.put(temp,cnt++);
            temp.setOnMouseClicked(event -> {
                Button btn=(Button)event.getSource();
                if(!breakpoints.contains(contentsMap.get(btn))) {
                    btn.setStyle("-fx-background-color: deepskyblue");
                    breakpoints.add(contentsMap.get(btn));
                }else {
                    btn.setStyle("-fx-background-color: whitesmoke");
                    breakpoints.remove(contentsMap.get(btn));
                    cancelBreakpoint(contentsMap.get(btn));
                }
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
    @FXML
    void onDebugClicked() throws RemoteException {
        clear();
        for(int i:breakpoints){
            RemoteController.getRuntimeServer().debugSetBreakpoint(i);
        }
        String[] results=RemoteController.getRuntimeServer().debug();
        if(!results[0].equals("finish")) {
            int PC = Integer.parseInt(results[0]);
            Button currentInstruction = getKey(contentsMap, PC);
            currentInstruction.setStyle("-fx-background-color: firebrick");
        }
        Label temp;
        for (int i = 2; i < results.length; i++) {
            temp = new Label(results[i]);
            stackPane.getChildren().add(temp);
            stackMap.put(temp, i - 2);
        }
        int pointer=Integer.parseInt(results[1]);
        Label currentStack=getKey(stackMap,pointer);
        currentStack.setStyle("-fx-background-color: aquamarine");
    }

    //utilities
    private <K> K getKey(Map<K,Integer> map,int value){
        for (Map.Entry<K,Integer> entry: map.entrySet()){
            if(value==entry.getValue()){
                return entry.getKey();
            }
        }
        return null;
    }

    private void cancelBreakpoint(int a)  {
        try {
            RemoteController.getRuntimeServer().debugRemoveBreakpoint(a);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    private void appendRefreshOutput(){
        outputArea.setText(new String(RemoteController.getIoProcessor().getOutput()));
    }

    private void clear(){
        stackMap.clear();
        stackPane.getChildren().clear();
    }
}
