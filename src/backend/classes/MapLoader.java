package classes;

import interfaces.Loader;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MapLoader extends DataLoader implements Loader {

    private enum LoadingDataType {
        NONE, HOSPITAL, MONUMENT, ROAD;
    }

    private LoadingDataType loadingMode = LoadingDataType.NONE;

    private static final int HOSPITAL_DATA_SIZE = 6;
    private static final int HOSPITAL_ID_INDEX = 0;
    private static final int HOSPITAL_NAME_INDEX = 1;
    private static final int HOSPITAL_X_INDEX = 2;
    private static final int HOSPITAL_Y_INDEX = 3;
    private static final int HOSPITAL_BEDS_AMOUNT_INDEX = 4;
    private static final int HOSPITAL_FREE_BEDS_AMOUNT_INDEX = 5;

    private static final int MONUMENT_DATA_SIZE = 4;
    private static final int MONUMENT_ID_INDEX = 0;
    private static final int MONUMENT_NAME_INDEX = 1;
    private static final int MONUMENT_X_INDEX = 2;
    private static final int MONUMENT_Y_INDEX = 3;

    private static final int ROAD_DATA_SIZE = 4;
    private static final int ROAD_ID_INDEX = 0;
    private static final int ROAD_FIRST_HOSPITAL_ID_INDEX = 1;
    private static final int ROAD_SECOND_HOSPITAL_ID_INDEX = 2;
    private static final int ROAD_DISTANCE_INDEX = 3;

    private ArrayList<Hospital> loadedHospitalsList = new ArrayList<Hospital>();
    private ArrayList<Monument> loadedMonumentList = new ArrayList<Monument>();
    private ArrayList<Road> loadedRoadsList = new ArrayList<Road>();

    @Override
    public void loadDataFromFile(String filePath) {
        if(loadData(filePath) && vaildateData()){
            for(Hospital hospital : loadedHospitalsList){
                DataBase.addHospital(hospital);
                DataBase.addBoundary((Boundary) hospital);
            }

            for(Monument monument : loadedMonumentList){
                DataBase.addMonument(monument);
                DataBase.addBoundary((Boundary) monument);
            }

            for(Road road : loadedRoadsList){
                DataBase.addRoad(road);
            }
            DataBase.addTerminalMessage("Map loaded");
        } else {
            DataBase.addTerminalMessage("Map loaded failed");
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
            loadingMode = LoadingDataType.MONUMENT;
        } else if (loadedLine.startsWith("# Drogi")){
            loadingMode = LoadingDataType.ROAD;
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
                if (!loadHospital()) {
                    return false;
                }
                break;
            case MONUMENT:
                if (!loadMonument()) {
                    return false;
                }
                break;
            case ROAD:
                if (!loadRoad()) {
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
                int hospitalFreeBedsAmount = parseInt(hospitalData, HOSPITAL_FREE_BEDS_AMOUNT_INDEX);
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

    private boolean loadMonument() {
        String[] monumentData = loadedLine.split(DATA_SPITER);
        if (monumentData.length == MONUMENT_DATA_SIZE) {
            try {
                int boundaryId = parseInt(monumentData, MONUMENT_ID_INDEX);
                String boundaryName = monumentData[MONUMENT_NAME_INDEX];
                double boundaryX = parseInt(monumentData, MONUMENT_X_INDEX);
                double boundaryY = parseInt(monumentData, MONUMENT_Y_INDEX);
                loadedMonumentList.add(new Monument(boundaryId, boundaryName, new Point2D.Double(boundaryX, boundaryY)));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            System.out.println("Niewłaściwa liczba danych dla objetków w linii " + lineNumber +":\n" + loadedLine);
        }
        return false;
    }

    private boolean loadRoad() {
        String[] roadData = loadedLine.split(DATA_SPITER);
        if (roadData.length == ROAD_DATA_SIZE) {
            try {
                int roadId = parseInt(roadData, ROAD_ID_INDEX);
                int roadFirstNodeId = parseInt(roadData, ROAD_FIRST_HOSPITAL_ID_INDEX);
                int roadSecondNodeId = parseInt(roadData, ROAD_SECOND_HOSPITAL_ID_INDEX);
                double roadDistance = parseInt(roadData, ROAD_DISTANCE_INDEX);
                loadedRoadsList.add(new Road(roadId, roadFirstNodeId, roadSecondNodeId, roadDistance));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            System.out.println("Niewłaściwa liczba danych dla drogi w linii " + lineNumber +":\n" + loadedLine);
        }
        return false;
    }
}
