package controllers;

import classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MapWindowController {

    public AnchorPane anchorPaneMapWindow;
    private MainWindowController mainWindowController;
    private double scaleX;
    private double scaleY;
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    @FXML
    void initialize() {
        scaleX = 1.0;
        scaleY = 1.0;
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    private void calculateBoundaries(double[] arrayOfBoundaries) {
        if (arrayOfBoundaries.length == 0) {
            return;
        }
        minX = Double.MAX_VALUE;
        minY = Double.MAX_VALUE;
        maxX = Double.MIN_VALUE;
        maxY = Double.MIN_VALUE;

        for(int i = 0; i < arrayOfBoundaries.length; i += 2) {
            if (minX > arrayOfBoundaries[i]) {
                minX = arrayOfBoundaries[i];
            } else if (maxX < arrayOfBoundaries[i]) {
                maxX = arrayOfBoundaries[i];
            }

            if (minY > arrayOfBoundaries[i+1]) {
                minY = arrayOfBoundaries[i+1];
            } else if (maxY < arrayOfBoundaries[i+1]) {
                maxY = arrayOfBoundaries[i+1];
            }
        }
    }

    double rescaleX(double x) {
        return ((x - minX) / (maxX - minX)) * anchorPaneMapWindow.getWidth();
    }

    double rescaleY(double y) {
        return ((y - minY) / (maxY - minY)) * anchorPaneMapWindow.getHeight();
    }

    private void printScale(double[] arrayOfBoundaries) {
        if (arrayOfBoundaries.length == 0) {
            return;
        }

        Text textXMin = new Text(rescaleX(minX + 5), 0, Double.toString(minX));
        Text textXMax = new Text(rescaleX(maxX), 0, Double.toString(maxX));
        Text textYMin = new Text(0, rescaleY(minY + 5), Double.toString(minY));
        Text textYMax = new Text(0, rescaleY(maxY), Double.toString(maxY));
//        anchorPaneMapWindow.setStyle("-fx-border-color: green; -fx-border-width: 1px 1px 1px 0px");
        anchorPaneMapWindow.getChildren().addAll(textXMin, textXMax, textYMin, textYMax);
    }

    public void printMap(){
        anchorPaneMapWindow.getChildren().clear();

        double[] arrayOfBoundaries = new double[2 * Country.getBoundariesNodes().size()];
        int count = 0;
        for(Boundary boundary : Country.getBoundariesNodes()) {
            arrayOfBoundaries[count] = boundary.getCords().getX();
            count++;
            arrayOfBoundaries[count] = boundary.getCords().getY();
            count++;
        }
        calculateBoundaries(arrayOfBoundaries);
        printScale(arrayOfBoundaries);
        for(int i = 0; i < arrayOfBoundaries.length; i += 2) {
            arrayOfBoundaries[i] = rescaleX(arrayOfBoundaries[i]);
            arrayOfBoundaries[i + 1] = rescaleY(arrayOfBoundaries[i + 1]);
        }
        Polygon polygon = new Polygon(arrayOfBoundaries);
        polygon.setFill(Color.LIGHTBLUE);
        anchorPaneMapWindow.getChildren().add(polygon);

        for (Intersection inter : DataBase.getIntersectionsList()) {
            Circle circle = new Circle(rescaleX(inter.getCords().getX()), rescaleY(inter.getCords().getY()), 5.f, Color.BLACK);
            anchorPaneMapWindow.getChildren().add(circle);
        }

        for(Monument monument : DataBase.getMonumentsList()){
            Circle circle = new Circle(rescaleX(monument.getCords().getX()), rescaleY(monument.getCords().getY()), 5.f, Color.BLUE);
            anchorPaneMapWindow.getChildren().add(circle);
        }

        for(Hospital hospital : DataBase.getHospitalsList()){
            Circle circle;
            if(hospital.getFreeBedsAmount() == 0)
                circle = new Circle(rescaleX(hospital.getCords().getX()), rescaleY(hospital.getCords().getY()), 5.f, Color.RED);
            else
                circle = new Circle(rescaleX(hospital.getCords().getX()), rescaleY(hospital.getCords().getY()), 5.f, Color.GREEN);

            anchorPaneMapWindow.getChildren().add(circle);
        }

        for(Road road : DataBase.getRoadsList()){
            Line line = new Line(rescaleX(road.getFirstNode().getCords().getX()), rescaleY(road.getFirstNode().getCords().getY()),
                    rescaleX(road.getSecondNode().getCords().getX()), rescaleY(road.getSecondNode().getCords().getY()));
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
