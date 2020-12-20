package controllers;

import classes.DataBase;
import classes.MapLoader;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class MapWindowController {

    private MainWindowController mainWindowController;

    public void loadHospitalsFromFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            MapLoader mapLoader = new MapLoader();
            mapLoader.loadDataFromFile(selectedFile.getAbsolutePath());

            DataBase.printHospitals();
            DataBase.printBoundary();
            DataBase.printRoads();

            mainWindowController.doActionsAfterLoadMap();
        }
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}
