package controllers;

import classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

public class MapWindowController {

    public AnchorPane anchorPaneMapWindow;
    private MainWindowController mainWindowController;

    @FXML
    void initialize() {

    }

    public void loadHospitalsFromFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            MapLoader mapLoader = new MapLoader();
            mapLoader.loadDataFromFile(selectedFile.getAbsolutePath());

            DataBase.printHospitals();
            DataBase.printMonuments();
            DataBase.printRoads();

            printMap();
            mainWindowController.doActionsAfterLoadMap();
        }
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    private void printMap(){
        double scale = 3.0;
        for(Monument monument : DataBase.getMonumentsList()){
            Circle circle = new Circle(monument.getCords().getX() * scale, monument.getCords().getY() * scale, 5.f, Color.BLUE);
            anchorPaneMapWindow.getChildren().add(circle);
        }

        for(Hospital hospital : DataBase.getHospitalsList()){
            Circle circle;
            if(hospital.getFreeBedsAmount() == 0)
                circle = new Circle(hospital.getCords().getX() * scale, hospital.getCords().getY() * scale, 5.f, Color.RED);
            else
                circle = new Circle(hospital.getCords().getX() * scale, hospital.getCords().getY() * scale, 5.f, Color.GREEN);

            anchorPaneMapWindow.getChildren().add(circle);
        }

        for(Road road : DataBase.getRoadsList()){
            Line line = new Line(road.getFirstHospital().getCords().getX() * scale, road.getFirstHospital().getCords().getY() * scale,
                    road.getSecondHospital().getCords().getX() * scale, road.getSecondHospital().getCords().getY() * scale);
            anchorPaneMapWindow.getChildren().add(line);
        }

        ArrayList<Hospital> hospitalsList = DataBase.getHospitalsList();
        ArrayList<Monument> monumentsList = new ArrayList<>();
        for(Monument monument : DataBase.getMonumentsList()) {
            Monument tempMonument = new Monument(monument.getId() + hospitalsList.size(), monument.getName(), monument.getCords());
            monumentsList.add(tempMonument);
        }

        double[] arrayOfBoundaries = new double[2 * DataBase.getBoundariesList().size()];
        int count = 0;
        for(Boundary boundary : DataBase.getBoundariesList()) {
            arrayOfBoundaries[count] = boundary.getCords().getX();
            count++;
            arrayOfBoundaries[count] = boundary.getCords().getY();
            count++;
        }
        Polygon polygon = new Polygon(arrayOfBoundaries);
        polygon.setFill(Color.LIGHTBLUE);
        anchorPaneMapWindow.getChildren().add(polygon);

        // create a Group
  //      Group group = new Group(circle);
    }
}
