package controllers;

import classes.DataBase;
import classes.PathSearcher;
import classes.Patient;
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

    private double simulationSpeed;
    private PathSearcher pathSearcher;

    @FXML
    void initialize() {
        symulationButton.setDisable(true);
    }

    public void setDisableSimulationButton(boolean value) {
            symulationButton.setDisable(value);
    }

    public void startSimulation(ActionEvent actionEvent){
        for (Patient patient : DataBase.getPatientsList()){
            System.out.println("Kolejny pacjent");
            pathSearcher = new PathSearcher(patient);
            pathSearcher.searchFirstHospital();
            System.out.println(pathSearcher.getCurrentHospital());
            while (pathSearcher.getCurrentHospital() != null && pathSearcher.getCurrentHospital().getFreeBedsAmount() == 0){
                ArrayList<Integer> temp = pathSearcher.searchNextHospital();
                System.out.println(temp);
                System.out.println(pathSearcher.getCurrentHospital());
            }
            pathSearcher.getCurrentHospital().setFreeBedsAmount(pathSearcher.getCurrentHospital().getFreeBedsAmount() - 1);
            if (pathSearcher.getCurrentHospital().getFreeBedsAmount() - 1 == 0)
                DataBase.getNode(pathSearcher.getCurrentHospital().getId()).setCanStop(false);
            DataBase.setAllNodesNotVisited();
        }
        simulationSpeed = speedSlider.getValue();
        System.out.println(simulationSpeed);
    }

    public double getSimulationSpeed(){return simulationSpeed;}

}
