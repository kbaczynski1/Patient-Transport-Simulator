package controllers;

import classes.DataBase;
import classes.PatientsLoader;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class PatientWindowController {


    public void loadPatientsFromFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            PatientsLoader patientsLoader = new PatientsLoader();
            patientsLoader.loadDataFromFile(selectedFile.getAbsolutePath());
            DataBase.printPatiens();
        }
    }
}
