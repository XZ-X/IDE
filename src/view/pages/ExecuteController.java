package view.pages;

import Data.GlobalConstant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import logic.remoteInterfaces.RemoteController;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by xuxiangzhe on 2017/6/24.
 * This page contains the function of run and debug{next, run, back}
 *
 */
public class ExecuteController implements Initializable {
    static String toExec;
    static String fileName;
    private Map<Button, Integer> contentsMap = new HashMap<>();
    private Map<Label, Integer> stackMap = new HashMap<>();
    private LinkedList<Integer> breakpoints = new LinkedList<>();

    @FXML
    FlowPane contentPane;
    @FXML
    HBox stackPane;
    @FXML
    TextArea inputArea, outputArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contentPane.getChildren().clear();
        Button temp;
        int cnt = 0;
        for (char c : toExec.toCharArray()) {
            temp = new Button("" + c);
            temp.setStyle("-fx-background-color: whitesmoke");
            contentsMap.put(temp, cnt++);
            temp.setOnMouseClicked(event -> {
                Button btn = (Button) event.getSource();
                if (!breakpoints.contains(contentsMap.get(btn))) {
                    btn.setStyle("-fx-background-color: deepskyblue");
                    breakpoints.add(contentsMap.get(btn));
                } else {
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
    void refreshOutput() {
        appendRefreshOutput();
        RemoteController.getIoProcessor().clearOutput();
    }

    @FXML
    void exec() throws RemoteException {
        RemoteController.getIoProcessor().putIn(inputArea.getText());
        RemoteController.getRuntimeServer().run();
        refreshOutput();
    }

    @FXML
    void terminate() throws RemoteException {
        clear();
        RemoteController.getRuntimeServer().terminate();
        RemoteController.getIoProcessor().clearInput();
        RemoteController.getIoProcessor().clearInput();
    }

    @FXML
    void onDebugClicked() throws RemoteException {
        clear();
        for (int i : breakpoints) {
            RemoteController.getRuntimeServer().debugSetBreakpoint(i);
        }
        RemoteController.getRuntimeServer().setCurrentFile(fileName);
        RemoteController.getIoProcessor().putIn(inputArea.getText());
        String[] results=RemoteController.getRuntimeServer().debug();
        switch (results[0]) {
            case GlobalConstant.DEBUG_TIME_OUT_MESSAGE:
                stackPane.getChildren().add(new Label("Time Out! You might write a foolish endless loop!\n Luckily, I've killed it for you:)"));
                break;
            case GlobalConstant.DEBUG_WRONG_MESSAGE:
                stackPane.getChildren().add(new Label("Opp!Your programme has run into some faults, please try again!"));
                break;
            default:
                //normal case
                showTheContext(results);
        }
    }

    @FXML
    void onContinueClicked() throws RemoteException {
        clear();
        for (int i : breakpoints) {
            RemoteController.getRuntimeServer().debugSetBreakpoint(i);
        }
        String[] results=RemoteController.getRuntimeServer().debugContinue();
        switch (results[0]) {
            case GlobalConstant.DEBUG_TIME_OUT_MESSAGE:
                stackPane.getChildren().add(new Label("Time Out! You might write a foolish endless loop!\n Luckily, I've killed it for you:)"));
                break;
            case GlobalConstant.DEBUG_WRONG_MESSAGE:
                stackPane.getChildren().add(new Label("Opp!Your programme has run into some faults, please try again!"));
                break;
            default:
                //normal case
                showTheContext(results);
        }
    }

    @FXML
    void debugNext() throws RemoteException {
        clear();
        showTheContext(RemoteController.getRuntimeServer().debugNext());
        inputArea.setText(new String(RemoteController.getIoProcessor().getAllInput()));
    }

    @FXML
    void debugBack() throws RemoteException {
        clear();
        showTheContext(RemoteController.getRuntimeServer().debugBack());
    }

    //utilities
    private <K> K getKey(Map<K, Integer> map, int value) {
        for (Map.Entry<K, Integer> entry : map.entrySet()) {
            if (value == entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void cancelBreakpoint(int a) {
        try {
            RemoteController.getRuntimeServer().debugRemoveBreakpoint(a);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //show the runtime context of a programme, including stack information and PC information, etc
    private void showTheContext(String[] context){
        if (!context[0].equals(GlobalConstant.DEBUG_FINISH)) {
            int PC = Integer.parseInt(context[0]);
            Button currentInstruction = getKey(contentsMap, PC);
            currentInstruction.setStyle("-fx-background-color: firebrick;");
        }
        Label temp;
        for (int i = 2; i < context.length; i++) {
            temp = new Label(context[i]);
            temp.setStyle("-fx-border-color: chocolate;");
            stackPane.getChildren().add(temp);
            stackMap.put(temp, i - 2);
        }
        int pointer = Integer.parseInt(context[1]);
        Label currentStack = getKey(stackMap, pointer);
        //On executing a PAdd() command, the stack will not have effective data, so there could be null pointer.
        if(currentStack!=null) {
            currentStack.setStyle(currentStack.getStyle()+"-fx-background-color: aquamarine;");
        }
    }

    private void appendRefreshOutput() {
        outputArea.setText(new String(RemoteController.getIoProcessor().getOutput()));
    }

    private void clear() {
        stackMap.clear();
        stackPane.getChildren().clear();
        for(Map.Entry<Button,Integer> entry:contentsMap.entrySet()){
            if(breakpoints.contains(entry.getValue())){
                entry.getKey().setStyle("-fx-background-color: deepskyblue;");
            }else {
                entry.getKey().setStyle("-fx-background-color: whitesmoke;");
            }
        }

    }
}
