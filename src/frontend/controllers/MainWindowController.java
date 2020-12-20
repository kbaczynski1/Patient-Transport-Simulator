package controllers;

import classes.DataBase;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainWindowController {

    @FXML
    private MapWindowController mapWindowController;

    @FXML
    private HospitalsWindowController hospitalsWindowController;

    @FXML
    void initialize() {
        mapWindowController.setMainWindowController(this);
    }

    public void doActionsAfterLoadMap(){
        hospitalsWindowController.updateHospitalTable();
        Thread readingThread = new Thread() {
            public void run() {
                int free = 0;
                while(true) {
                    DataBase.getHospital(1).setFreeBedsAmount(free);
                    free++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        readingThread.setDaemon(true);
        readingThread.start();
    }
}
