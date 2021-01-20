package controllers;

import classes.DataBase;
import classes.PatientsLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;

import java.io.File;

public class PatientWindowController {

    @FXML
    private Spinner removePatientValueId;
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
        DataBase.addTerminalMessage("remove patient [Id:" + removePatientValueId.getValue() + "]");
    }
}
