package controllers;

import classes.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class TerminalWindowController {

    @FXML
    private TextArea TerminalTextArea;

    @FXML
    void initialize() {

        Thread refreshTerminalThread = new Thread(() -> {
            int messagesCounter = 0;
            while(true) {
                while(messagesCounter < DataBase.getTerminalMessagesList().size()){
                    TerminalTextArea.appendText(DataBase.getTerminalMessagesList().get(messagesCounter) + "\n");
                    messagesCounter++;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        refreshTerminalThread.setDaemon(true);
        refreshTerminalThread.start();


    }
}
