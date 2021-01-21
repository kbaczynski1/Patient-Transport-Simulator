package controllers;

import classes.DataBase;
import classes.Patient;
import classes.PatientsLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import java.util.ArrayList;
import javafx.stage.FileChooser;

import java.io.File;

public class PatientWindowController {

    @FXML
    private Spinner<Integer> removePatientValueId;
    @FXML
    private Spinner addPatientValueX;
    @FXML
    private Spinner addPatientValueY;
    @FXML
    private Spinner addPatientValueId;

//    .setValueFactory(
//                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255));

    @FXML
    void initialize() {
        addPatientValueX.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
        addPatientValueY.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
        addPatientValueId.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
        removePatientValueId.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));

    }

    public void addPatientAction(ActionEvent actionEvent) {

        DataBase.addTerminalMessage("add patient [X:" + addPatientValueX.getValue() + " Y:" + addPatientValueY.getValue() + " Id:" + addPatientValueId.getValue() +"]");
    }

    public void removeatientAction(ActionEvent actionEvent) {
        int patientId = removePatientValueId.getValue();
        ArrayList<Patient> patientsList = DataBase.getPatientsList();
        for(int i = 0; i < patientsList.size(); i++) {
            if (patientsList.get(i).getId() == patientId) {
                DataBase.addTerminalMessage("remove patient [Id:" + removePatientValueId.getValue() + "]");
                patientsList.remove(i);
                // jeżeli zakładamy, że istnieje tylko jeden pacjent z danym id
                return;
            }
        }
        DataBase.addTerminalMessage("Patient " + patientId + " is not currently in queue (Nothing to remove)");
    }
}
