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
import java.util.ArrayList;


public class MapWindowController {

    public AnchorPane anchorPaneMapWindow;
    private MainWindowController mainWindowController;
    double scale = 3.0;

    @FXML
    void initialize() {

    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }


    public void printMap(){
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
            Text text = new Text();
            text.setText(Double.toString(Math.round(road.getDistance() * 100)/100));
            text.setX(((road.getFirstNode().getCords().getX() + road.getSecondNode().getCords().getX())/2)*scale);
            text.setY(((road.getFirstNode().getCords().getY() + road.getSecondNode().getCords().getY())/2)*scale);
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
        Circle circle = new Circle(patient.getX() * scale, patient.getY() * scale, 5.f, Color.ORANGE);
        circle.setVisible(false);
        anchorPaneMapWindow.getChildren().add(circle);
        Path path = new Path();
        path.getElements().add(new MoveTo(patient.getX() * scale, patient.getY() * scale));
        for (Integer nodeId : nodesPath){
            path.getElements().add(new LineTo(DataBase.getNode(nodeId).getCords().getX() * scale, DataBase.getNode(nodeId).getCords().getY() * scale));
        }
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000/speed));
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        return pathTransition;

    }
}
