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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
        DataBase.addTerminalMessage("Starting simulation");
        ArrayList<PathTransition> pathList = new ArrayList<>();
        int patientsNotInBoundaries = 0;
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
                if (pathSearcher.getCurrentHospital().getFreeBedsAmount() - 1 == 0) {
                    DataBase.getNode(pathSearcher.getCurrentHospital().getId()).setCanStop(false);
                    mapWindowController.drawHospitalRed(
                            pathSearcher.getCurrentHospital().getCords().getX(),
                            pathSearcher.getCurrentHospital().getCords().getY());

                }
                DataBase.setAllNodesNotVisited();
            } else {
                patientsNotInBoundaries++;
            }
        }
        if (patientsNotInBoundaries != 0) {
            DataBase.addTerminalMessage( patientsNotInBoundaries + " patients were not in country. Ommiting.");
        }

        Thread refreshSimulationThread = new Thread(() -> {
            int animationCounter = 0;
            while(animationCounter < pathList.size()) {
                pathList.get(animationCounter).getNode().setVisible(true);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pathList.get(animationCounter).play();
                DataBase.addTerminalMessage("Patient " + animationCounter + " taken to hospital");
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
        refreshSimulationThread.setDaemon(true);
        refreshSimulationThread.start();
        DataBase.addTerminalMessage("Simulation complete!");
    }

    public double getSimulationSpeed(){return simulationSpeed;}

}
