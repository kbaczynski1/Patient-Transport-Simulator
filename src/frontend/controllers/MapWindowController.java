package controllers;

import classes.*;
import javafx.animation.PathTransition;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Path;

import javafx.scene.text.Text;

import javafx.util.Duration;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;


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
            Text text = new Text();
            text.setText(Double.toString(Math.round(road.getDistance() * 100)/100));
            text.setX(rescaleX(((road.getFirstNode().getCords().getX() + road.getSecondNode().getCords().getX())/2)));
            text.setY(rescaleY(((road.getFirstNode().getCords().getY() + road.getSecondNode().getCords().getY())/2)));
            anchorPaneMapWindow.getChildren().add(text);
        }

        ArrayList<Hospital> hospitalsList = DataBase.getHospitalsList();
        ArrayList<Monument> monumentsList = new ArrayList<>();
        for(Monument monument : DataBase.getMonumentsList()) {
            Monument tempMonument = new Monument(monument.getId() + hospitalsList.size(), monument.getName(), monument.getCords());
            monumentsList.add(tempMonument);
        }

    }

    public PathTransition drawPatient(Patient patient, ArrayList<Integer> nodesPath, double speed){
        Circle circle = new Circle(rescaleX(patient.getX()), rescaleY(patient.getY()), 5.f, Color.ORANGE);
        circle.setVisible(false);
        anchorPaneMapWindow.getChildren().add(circle);
        Path path = new Path();
        path.getElements().add(new MoveTo(rescaleX(patient.getX()), rescaleY(patient.getY())));
        for (Integer nodeId : nodesPath){
            path.getElements().add(new LineTo(rescaleX(DataBase.getNode(nodeId).getCords().getX()), rescaleY(DataBase.getNode(nodeId).getCords().getY())));
        }
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000/speed));
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        return pathTransition;

    }
}
