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
    private Spinner<Double> addPatientValueX;
    @FXML
    private Spinner<Double> addPatientValueY;
    @FXML
    private Spinner<Integer> addPatientValueId;

//    .setValueFactory(
//                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255));

    @FXML
    void initialize() {
        addPatientValueX.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000));
        addPatientValueY.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000));
        addPatientValueId.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
        removePatientValueId.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));

    }

    public void addPatientAction(ActionEvent actionEvent) {

        int patientId = addPatientValueId.getValue();
        if(DataBase.getPatient(patientId) != null) {
            DataBase.addTerminalMessage("Patient " + patientId + " already exists in base");
            ArrayList<Patient> patients = DataBase.getPatientsList();
            int lastFreeId = -1;
            for(Patient patient : patients) {
                if(patient.getId() > lastFreeId) {
                    lastFreeId = patient.getId();
                }
            }
            patientId = lastFreeId + 1;
            DataBase.addTerminalMessage("Adding patient with first free id (=" + patientId + ").");
        }
        double x = addPatientValueX.getValue();
        double y = addPatientValueY.getValue();
        Patient newPatient = new Patient(patientId, "Manual added:" + patientId, x, y);
        DataBase.addPatient(newPatient);
        DataBase.addTerminalMessage("Setting id counter to next value");
        addPatientValueId.getValueFactory().setValue(patientId+1);

        DataBase.addTerminalMessage("Patient with id " + patientId + " added successfully");
    }

    public void removeAllAction(ActionEvent actionEvent) {
        DataBase.clearPatients();
        DataBase.addTerminalMessage("Removed all patients (if existed)");
    }

    public void removeatientAction(ActionEvent actionEvent) {
        int patientId = removePatientValueId.getValue();
        ArrayList<Patient> patientsList = DataBase.getPatientsList();
        for(int i = 0; i < patientsList.size(); i++) {
            if (patientsList.get(i).getId() == patientId) {
                DataBase.addTerminalMessage("Patient [Id:" + removePatientValueId.getValue() + "] removed");
                patientsList.remove(i);
                // jeżeli zakładamy, że istnieje tylko jeden pacjent z danym id
                return;
            }
        }
        DataBase.addTerminalMessage("Patient " + patientId + " is not currently in queue (Nothing to remove)");
    }
}
