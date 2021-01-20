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
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MapWindowController {

    public AnchorPane anchorPaneMapWindow;
    private MainWindowController mainWindowController;

    @FXML
    void initialize() {

    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void printMap(){
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

        for (Intersection inter : DataBase.getIntersectionsList()) {
            Circle circle = new Circle(inter.getCords().getX() * 3.0, inter.getCords().getY() * 3.0, 5.f, Color.BLACK);
            anchorPaneMapWindow.getChildren().add(circle);
        }

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
            Line line = new Line(road.getFirstNode().getCords().getX() * scale, road.getFirstNode().getCords().getY() * scale,
                    road.getSecondNode().getCords().getX() * scale, road.getSecondNode().getCords().getY() * scale);
            anchorPaneMapWindow.getChildren().add(line);
        }

        ArrayList<Hospital> hospitalsList = DataBase.getHospitalsList();
        ArrayList<Monument> monumentsList = new ArrayList<>();
        for(Monument monument : DataBase.getMonumentsList()) {
            Monument tempMonument = new Monument(monument.getId() + hospitalsList.size(), monument.getName(), monument.getCords());
            monumentsList.add(tempMonument);
        }

        /* test działania najbliżeszego szpitala
        DataBase.addPatient(new Patient(0, "Edek", new Point2D.Double(11.0,31.0)));
        PathSearcher pathSearcher = new PathSearcher(DataBase.getPatient(0));
        pathSearcher.searchFirstHospital();
        Hospital hos = pathSearcher.getCurrentHospital();
        Circle circle = new Circle(hos.getCords().getX() * scale, hos.getCords().getY() * scale, 5.f, Color.ORANGE);
        anchorPaneMapWindow.getChildren().add(circle);
        */

    }
}
