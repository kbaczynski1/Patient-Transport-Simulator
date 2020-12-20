package classes;

import interfaces.Loader;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MapLoader extends DataLoader implements Loader {

    private enum LoadingDataType {
        NONE, HOSPITAL;
    }

    private LoadingDataType loadingMode = LoadingDataType.NONE;

    private static final int HOSPITAL_DATA_SIZE = 6;
    private static final int HOSPITAL_ID_INDEX = 0;
    private static final int HOSPITAL_NAME_INDEX = 1;
    private static final int HOSPITAL_X_INDEX = 2;
    private static final int HOSPITAL_Y_INDEX = 3;
    private static final int HOSPITAL_BEDS_AMOUNT_INDEX = 4;
    private static final int HOSPITAL_FREE_BEEDS_AMOUNT_INDEX = 5;

    private ArrayList<Hospital> loadedHospitalsList = new ArrayList<Hospital>();

    @Override
    public void loadDataFromFile(String filePath) {
        if(loadData(filePath) && vaildateData()){
            for(Hospital hospital : loadedHospitalsList){
                DataBase.addHospital(hospital);
            }
        }
    }

    @Override
    public boolean vaildateData() {
        return true;
    }

    @Override
    protected void checkComment() {
        if (loadedLine.startsWith("# Szpitale")) {
            loadingMode = LoadingDataType.HOSPITAL;
        } else if (loadedLine.startsWith("# Obiekty")){
            loadingMode = LoadingDataType.NONE;
        } else if (loadedLine.startsWith("# Drogi")){
            loadingMode = LoadingDataType.NONE;
        } else {
            // Other comment
        }
    }

    @Override
    protected boolean loadObject() {
        switch (loadingMode) {
            case NONE:
                break;
            case HOSPITAL:
                if (loadHospital() == false) {
                    return false;
                }
                break;
        }
        return true;
    }

    private boolean loadHospital() {
        String[] hospitalData = loadedLine.split(DATA_SPITER);
        if (hospitalData.length == HOSPITAL_DATA_SIZE) {
            try {
                int hospitalId = parseInt(hospitalData, HOSPITAL_ID_INDEX);
                String hospitalName = hospitalData[HOSPITAL_NAME_INDEX];
                double hospitalX = parseInt(hospitalData, HOSPITAL_X_INDEX);
                double hospitalY = parseInt(hospitalData, HOSPITAL_Y_INDEX);
                int hospitalBedsAmount = parseInt(hospitalData, HOSPITAL_BEDS_AMOUNT_INDEX);
                int hospitalFreeBedsAmount = parseInt(hospitalData, HOSPITAL_FREE_BEEDS_AMOUNT_INDEX);
                loadedHospitalsList.add(new Hospital(hospitalId, hospitalName, new Point2D.Double(hospitalX, hospitalY), hospitalBedsAmount, hospitalFreeBedsAmount));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            System.out.println("Niewłaściwa liczba danych dla szpitala w linii " + lineNumber +":\n" + loadedLine);
        }
        return false;
    }
}
