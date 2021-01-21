package controllers;

import classes.DataBase;
import classes.Patient;
import classes.PatientsLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import java.util.ArrayList;
import javafx.util.StringConverter;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.function.UnaryOperator;

public class PatientWindowController {

    @FXML
    private Spinner<Integer> removePatientValueId;
    @FXML
    private Spinner<Double> addPatientValueX;
    @FXML
    private Spinner<Double> addPatientValueY;
    @FXML
    private Spinner<Integer> addPatientValueId;

    @FXML
    void initialize() {
        UnaryOperator<Change> doubleFilter = new UnaryOperator<Change>() {
            @Override
            public Change apply(Change t) {

                String newText = t.getControlNewText() ;
                if (newText.matches("-?[0-9]*\\.?[0-9]*")) {
                    return t ;
                }
                return null ;
            }
        };

        UnaryOperator<Change> intFilter = new UnaryOperator<Change>() {
            @Override
            public Change apply(Change t) {

                String newText = t.getControlNewText() ;
                if (newText.matches("[0-9]{0,7}")) {
                    return t ;
                }
                return null ;
            }
        };

        StringConverter<Double> doubleConverter = new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return object.toString() ;
            }

            @Override
            public Double fromString(String string){
                if (string.isEmpty() || ".".equals(string) || "-".equals(string) || "-.".equals(string)) {
                    return 0.0;
                } else {
                    return new Double(string);
                }
            }
        };

        StringConverter<Integer> intConverter = new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object.toString() ;
            }

            @Override
            public Integer fromString(String string){
                if (string.isEmpty() || ".".equals(string) || "-".equals(string) || "-.".equals(string)) {
                    return 0;
                } else {
                    return new Integer(string);
                }
            }
        };
        SpinnerValueFactory<Double> xValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory( -1000000, 1000000,0);
        SpinnerValueFactory<Double> yValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory( -1000000, 1000000,0);
        SpinnerValueFactory<Integer> addIntValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory( 0, 1000000);
        SpinnerValueFactory<Integer> removeIntValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory( 0, 1000000);

        xValueFactory.setConverter(doubleConverter);
        yValueFactory.setConverter(doubleConverter);
        addIntValueFactory.setConverter(intConverter);
        removeIntValueFactory.setConverter(intConverter);

        addPatientValueX.setValueFactory(xValueFactory);
        addPatientValueY.setValueFactory(yValueFactory);
        addPatientValueId.setValueFactory(addIntValueFactory);
        removePatientValueId.setValueFactory(removeIntValueFactory);

        addPatientValueX.getEditor().setTextFormatter(new TextFormatter<>(doubleConverter, 0.0, doubleFilter));
        addPatientValueY.getEditor().setTextFormatter(new TextFormatter<>(doubleConverter, 0.0, doubleFilter));
        addPatientValueId.getEditor().setTextFormatter(new TextFormatter<>(intConverter, 0, intFilter));
        removePatientValueId.getEditor().setTextFormatter(new TextFormatter<>(intConverter, 0, intFilter));

        addPatientValueY.setEditable(true);
        addPatientValueX.setEditable(true);
        addPatientValueId.setEditable(true);
        removePatientValueId.setEditable(true);

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
                return;
            }
        }
        DataBase.addTerminalMessage("Patient " + patientId + " is not currently in queue (Nothing to remove)");
    }
}
