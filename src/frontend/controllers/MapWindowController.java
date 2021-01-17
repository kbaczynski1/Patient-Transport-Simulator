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

import java.awt.geom.Point2D;
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

            IntersectionDetector intersectionDetector = new IntersectionDetector(DataBase.getRoadsList());
            for (Road road : DataBase.getRoadsList()) {
                road.initValue();
            }
//            intersectionDetector.detect();
            intersectionDetector.scanIntersections();
            intersectionDetector.printIntersections();
            for (Point2D point : intersectionDetector.getIntersections()) {
                Circle circle = new Circle(point.getX() * 3.0, point.getY() * 3.0, 5.f, Color.BLACK);
                anchorPaneMapWindow.getChildren().add(circle);
            }

            DataBase.printHospitals();
            DataBase.printMonuments();
            DataBase.printRoads();

            Country.calculateBoundaries(DataBase.getBoundariesList());
            printMap();
            mainWindowController.doActionsAfterLoadMap();
        }
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    private void printMap(){
        double scale = 3.0;

        double[] arrayOfBoundaries = new double[2 * Country.getBoundariesNodes().size()];
        int count = 0;
        for(Boundary boundary : Country.getBoundariesNodes()) {
            arrayOfBoundaries[count] = boundary.getCords().getX()*scale;
            count++;
            arrayOfBoundaries[count] = boundary.getCords().getY()*scale;
            count++;
        }
        Polygon polygon = new Polygon(arrayOfBoundaries);
        polygon.setFill(Color.LIGHTBLUE);
        anchorPaneMapWindow.getChildren().add(polygon);

        for(Monument monument : DataBase.getMonumentsList()){
            Circle circle = new Circle(monument.getCords().getX() * scale, monument.getCords().getY() * scale, 5.f, Color.BLUE);
            anchorPaneMapWindow.getChildren().add(circle);
        }

<<<<<<< HEAD
        for(Hospital hospital : DataBase.getHospitalsList()){
            Circle circle;
            if(hospital.getFreeBedsAmount() == 0)
                circle = new Circle(hospital.getCords().getX() * scale, hospital.getCords().getY() * scale, 5.f, Color.RED);
            else
                circle = new Circle(hospital.getCords().getX() * scale, hospital.getCords().getY() * scale, 5.f, Color.GREEN);

            anchorPaneMapWindow.getChildren().add(circle);
        }
=======
//        for(Hospital hospital : DataBase.getHospitalsList()){
//            Circle circle = new Circle(hospital.getCords().getX() * scale, hospital.getCords().getY() * scale, 5.f, Color.RED);
//            anchorPaneMapWindow.getChildren().add(circle);
//        }
>>>>>>> 5bc89e2bbb616d3e8507d80fffa375d996e8108f

        for(Road road : DataBase.getRoadsList()){
            Line line = new Line(road.getFirstHospital().getCords().getX() * scale, road.getFirstHospital().getCords().getY() * scale,
                    road.getSecondHospital().getCords().getX() * scale, road.getSecondHospital().getCords().getY() * scale);
            anchorPaneMapWindow.getChildren().add(line);
        }

<<<<<<< HEAD
        ArrayList<Hospital> hospitalsList = DataBase.getHospitalsList();
        ArrayList<Monument> monumentsList = new ArrayList<>();
        for(Monument monument : DataBase.getMonumentsList()) {
            Monument tempMonument = new Monument(monument.getId() + hospitalsList.size(), monument.getName(), monument.getCords());
            monumentsList.add(tempMonument);
        }


=======

//        for(Intersection intersection : DataBase.getIntersectionsList()){
//            Circle circle = new Circle(intersection.getCords().getX() * scale, intersection.getCords().getY() * scale, 5.f, Color.BLACK);
//            anchorPaneMapWindow.getChildren().add(circle);
//        }
>>>>>>> 5bc89e2bbb616d3e8507d80fffa375d996e8108f

        // create a Group
  //      Group group = new Group(circle);
    }
}
