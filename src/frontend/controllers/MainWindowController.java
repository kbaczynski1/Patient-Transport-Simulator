package controllers;

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
    }
}
