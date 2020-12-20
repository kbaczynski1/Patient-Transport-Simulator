package controllers;

import classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;

import java.io.File;

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
            DataBase.printBoundary();
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
        for(Boundary boundary : DataBase.getBoundaryList()){
            Circle circle = new Circle(boundary.getCords().getX() * scale, boundary.getCords().getY() * scale, 5.f, Color.BLUE);
            anchorPaneMapWindow.getChildren().add(circle);
        }

        for(Hospital hospital : DataBase.getHospitalsList()){
            Circle circle = new Circle(hospital.getCords().getX() * scale, hospital.getCords().getY() * scale, 5.f, Color.RED);
            anchorPaneMapWindow.getChildren().add(circle);
        }

        for(Road road : DataBase.getRoadsList()){
            Line line = new Line(road.getFirstHospital().getCords().getX() * scale, road.getFirstHospital().getCords().getY() * scale,
                    road.getSecondHospital().getCords().getX() * scale, road.getSecondHospital().getCords().getY() * scale);
            anchorPaneMapWindow.getChildren().add(line);
        }

        // create a Group
  //      Group group = new Group(circle);
    }
}
