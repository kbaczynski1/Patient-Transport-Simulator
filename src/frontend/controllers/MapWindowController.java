package controllers;

import classes.DataBase;
import classes.MapLoader;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class MapWindowController {
    public void loadHospitalsFromFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        MapLoader mapLoader = new MapLoader();
        mapLoader.loadDataFromFile(selectedFile.getAbsolutePath());

        DataBase.printHospitals();
        DataBase.printBoundary();
        DataBase.printRoads();
    }
}
