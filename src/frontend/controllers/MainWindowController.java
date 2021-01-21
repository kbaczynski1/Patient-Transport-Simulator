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

    // TA LINIJKA
//    @FXML
//    private SimulatorSettingsWindowController simulatorSettingsWindowController;

    @FXML
    void initialize() {
        mapWindowController.setMainWindowController(this);
    }

    public void doActionsAfterLoadMap(){
        hospitalsWindowController.updateHospitalTable();
//        Thread readingThread = new Thread() {
//            public void run() {
//                int free = 0;
//                while(true) {
//                    DataBase.getHospital(1).setFreeBedsAmount(free);
//                    free++;
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        readingThread.setDaemon(true);
//        readingThread.start();
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


        if(selectedFile != null){
            MapLoader mapLoader = new MapLoader();
            mapLoader.loadDataFromFile(selectedFile.getAbsolutePath());

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
            // TA LINIJKA
//            simulatorSettingsWindowController.toggleSimulationButton();
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
