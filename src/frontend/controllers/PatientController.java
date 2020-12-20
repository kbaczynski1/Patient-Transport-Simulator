package controllers;

import classes.DataBase;
import classes.PatientsLoader;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class PatientController {


    public void loadPatientsFromFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        PatientsLoader patientsLoader = new PatientsLoader();
        patientsLoader.loadDataFromFile(selectedFile.getAbsolutePath());
        DataBase.printPatiens();
    }
}
