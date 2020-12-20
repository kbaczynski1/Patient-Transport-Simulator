package classes;

import interfaces.Loader;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PatientsLoader extends DataLoader implements Loader {
    private enum LoadingDataType {
        NONE, PATIENT;
    }

    private LoadingDataType loadingMode = LoadingDataType.NONE;

    private static final int PATIENT_ID_INDEX = 0;
    private static final int PATIENT_X_INDEX = 1;
    private static final int PATIENT_Y_INDEX = 2;

    private ArrayList<Patient> loadedPatiensList = new ArrayList<Patient>();

    @Override
    public void loadDataFromFile(String filePath) {
        if(loadData(filePath)){
            for(Patient patient : loadedPatiensList){
                DataBase.addPatient(patient);
            }
        }
    }

    @Override
    public void vaildateData(String filePath) {

    }



    @Override
    protected void checkComment() {
        if (loadedLine.startsWith("# Pacjenci")) {
            loadingMode = LoadingDataType.PATIENT;
        } else {
            // Other comment
        }
    }

    @Override
    protected boolean loadObject() {
        switch (loadingMode) {
            case NONE:
                break;
            case PATIENT:
                if (loadPatient() == false) {
                    return false;
                }
                break;
        }
        return true;
    }

    private boolean loadPatient() {
        String[] patientData = loadedLine.split(DATA_SPITER);
        if (patientData.length == PATIENT_DATA_SIZE) {
            try {
                int patientId = parseInt(patientData, PATIENT_ID_INDEX);
                double patientX = parseInt(patientData, PATIENT_X_INDEX);
                double patientY = parseInt(patientData, PATIENT_Y_INDEX);
                loadedPatiensList.add(new Patient(patientId, "", new Point2D.Double(patientX, patientY)));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            System.out.println("Niewłaściwa liczba danych dla pacienta w linii " + lineNumber +":\n" + loadedLine);
        }
        return false;
    }

}
