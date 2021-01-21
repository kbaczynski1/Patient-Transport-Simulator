package controllers;

import classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MainWindowController {

    @FXML
    private MapWindowController mapWindowController;

    @FXML
    private HospitalsWindowController hospitalsWindowController;

    @FXML
    private SimulatorSettingsWindowController simulationSettingsWindowController;

    @FXML
    void initialize() {
        mapWindowController.setMainWindowController(this);
        simulationSettingsWindowController.setMapWindowController(mapWindowController);
    }

    public void doActionsAfterLoadMap(){
        hospitalsWindowController.updateHospitalTable();
    }

    public void loadMapFromFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        DataBase.clearHospitals();
        DataBase.clearRoads();
        DataBase.clearMonuments();
        DataBase.clearBoundaries();
        DataBase.clearIntersections();
        DataBase.clearNodes();
        Country.clearBoundaries();
        simulationSettingsWindowController.setDisableSimulationButton(true);


        if(selectedFile != null){
            MapLoader mapLoader = new MapLoader();
            if (!mapLoader.loadDataFromFile(selectedFile.getAbsolutePath()))
                return;

            IntersectionDetector intersectionDetector = new IntersectionDetector(DataBase.getRoadsList());
            for (Road road : DataBase.getRoadsList()) {
                road.initValue();
            }
            intersectionDetector.scanIntersections();
            intersectionDetector.printIntersections();
            DataBase.addIntesection(intersectionDetector.getIntersections());
            DataBase.divideRoads();

            DataBase.printHospitals();
            DataBase.printMonuments();
            DataBase.printRoads();

            Country.calculateBoundaries(DataBase.getBoundariesList());
            mapWindowController.printMap();
            doActionsAfterLoadMap();
            simulationSettingsWindowController.setDisableSimulationButton(false);
        }
    }

    public void loadPatientsFromFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        DataBase.clearPatients();
        if(selectedFile != null){
            PatientsLoader patientsLoader = new PatientsLoader();
            patientsLoader.loadDataFromFile(selectedFile.getAbsolutePath());
            DataBase.printPatiens();
        }
    }

}
