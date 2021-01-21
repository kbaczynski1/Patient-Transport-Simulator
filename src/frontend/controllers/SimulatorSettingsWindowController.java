package controllers;

import classes.DataBase;
import classes.PathSearcher;
import classes.Patient;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

public class SimulatorSettingsWindowController {

    @FXML
    private Slider speedSlider;

    @FXML
    private Button symulationButton;

    @FXML
    private MapWindowController mapWindowController;

    private double simulationSpeed;
    private PathSearcher pathSearcher;

    @FXML
    void initialize() {
        symulationButton.setDisable(true);
    }

    public void setDisableSimulationButton(boolean value) {
            symulationButton.setDisable(value);
    }

    public void setMapWindowController(MapWindowController mapWindowController){
        this.mapWindowController = mapWindowController;
    }

    public void startSimulation(ActionEvent actionEvent){
        ArrayList<PathTransition> pathList = new ArrayList<>();
        for (Patient patient : DataBase.getPatientsList()){
            pathSearcher = new PathSearcher(patient);
            if (pathSearcher.checkIfPatientIsInCountry()) {
                ArrayList<Integer> path = new ArrayList<>();
                pathSearcher.searchFirstHospital();
                path.add(pathSearcher.getCurrentHospital().getId());
                while (pathSearcher.getCurrentHospital() != null && pathSearcher.getCurrentHospital().getFreeBedsAmount() == 0) {
                    path.addAll(pathSearcher.searchNextHospital());
                }
                pathList.add(mapWindowController.drawPatient(patient, path, speedSlider.getValue()));
                pathSearcher.getCurrentHospital().setFreeBedsAmount(pathSearcher.getCurrentHospital().getFreeBedsAmount() - 1);
                if (pathSearcher.getCurrentHospital().getFreeBedsAmount() - 1 == 0)
                    DataBase.getNode(pathSearcher.getCurrentHospital().getId()).setCanStop(false);
                DataBase.setAllNodesNotVisited();
            }
        }
        Thread refreshTerminalThread = new Thread(() -> {
            int animationCounter = 0;
            while(animationCounter < pathList.size()) {
                pathList.get(animationCounter).getNode().setVisible(true);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pathList.get(animationCounter).play();
                while(pathList.get(animationCounter).getStatus() == Animation.Status.RUNNING) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pathList.get(animationCounter).getNode().setVisible(false);
                animationCounter += 1;
            }
        });
        refreshTerminalThread.setDaemon(true);
        refreshTerminalThread.start();
    }

    public double getSimulationSpeed(){return simulationSpeed;}

}
