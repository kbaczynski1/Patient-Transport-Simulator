package classes;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class IntersectionEvent {
    private Point2D point;
    private ArrayList<Road> roads;
    private double value;
    private int type;

    public Point2D getPoint() {
        return point;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    IntersectionEvent(Point2D point, Road road, int type) {
        this.point = point;
        this.roads = new ArrayList<Road>(Arrays.asList(road));
        this.value = point.getX();
        this.type = type;
    }

    IntersectionEvent(Point2D point, ArrayList<Road> roads, int type) {
        this.point = point;
        this.roads = roads;
        this.value = point.getX();
        this.type = type;
    }


}
