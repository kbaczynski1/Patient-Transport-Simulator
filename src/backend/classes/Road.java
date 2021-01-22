package classes;

import javax.sound.sampled.Line;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;

public class Road  implements Comparable<Road>{
    private int id;
    private int firstNodeId;
    private int secondNodeId;
    private double value;
    private double distance;

    Road(int id, int firstNodeId, int secondNodeId, double distance){
        this.id = id;
        this.firstNodeId = firstNodeId;
        this.secondNodeId = secondNodeId;
        this.distance = distance;
//        this.calculateValue(this.getStartPoint().getX());
    }

    @Override
    public String toString() {
        return "[" + id + "] " +"\tFirst Node: "+ firstNodeId + "\tSecond Node: " + secondNodeId + "\tDistance: " + distance;
    }

    public void initValue() {
        this.calculateValue(this.getStartPoint().getX());
    }

    public int getId() {
        return id;
    }

    public Node getFirstNode() {
        return DataBase.getNode(firstNodeId);
    }

    public int getFirstNodeId() {
        return firstNodeId;
    }

    public Node getSecondNode() {
        return DataBase.getNode(secondNodeId);
    }

    public int getSecondNodeId() {
        return secondNodeId;
    }

    public double getDistance() {
        return distance;
    }

    public Point2D.Double getStartPoint() {
        if (this.getFirstNode().getCords().getX() <= this.getSecondNode().getCords().getX()) {
            return this.getFirstNode().getCords();
        } else {
            return getSecondNode().getCords();
        }
    }

    public Point2D.Double getEndPoint() {
        if (getFirstNode().getCords().getX() <= getSecondNode().getCords().getX()) {
            return getSecondNode().getCords();
        } else {
            return getFirstNode().getCords();
        }
    }

    public void calculateValue(double currentSwipeLinePosition) {
        double x1 = this.getStartPoint().getX();
        double x2 = this.getEndPoint().getX();
        double y1 = this.getStartPoint().getY();
        double y2 = this.getEndPoint().getY();
        this.value = y1 + (((y2 - y1) / (x2 - x1)) * (currentSwipeLinePosition - x1));
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }


    @Override
    public int compareTo(Road road) {
        double current_x_cord = Math.min(getFirstNode().getCords().getX(), getSecondNode().getCords().getX());
        double road_x_cord = Math.min(road.getFirstNode().getCords().getX(), road.getSecondNode().getCords().getX());
        return Double.compare(current_x_cord, road_x_cord);
    }

    // TODO move it somewhere else, should be checked while parsing
    public boolean checkHospitalsOverlap(double x1, double y1, double x2, double y2) {
        if(x1 == x2 && y1 == y2) {
            return true;
        }
        return false;
    }
    // TODO move it somewhere else or make it prettier
    public Intersection doesIntersect(Road road) {
        double first_x_start = this.getFirstNode().getCords().getX();
        double first_y_start = this.getFirstNode().getCords().getY();
        double first_x_end = this.getSecondNode().getCords().getX();
        double first_y_end = this.getSecondNode().getCords().getY();
        int id1 = this.getId();

        double second_x_start = road.getFirstNode().getCords().getX();
        double second_y_start = road.getFirstNode().getCords().getY();
        double second_x_end = road.getSecondNode().getCords().getX();
        double second_y_end = road.getSecondNode().getCords().getY();
        int id2 = road.getId();

        // TODO rewrite since it's ugly, this will be checked while parsing
        // ? can road start and end in same hospital?
        // check if points overlap (we don't want to take them as intersections
        if(checkHospitalsOverlap(first_x_start, first_y_start, second_x_start, second_y_start) ||
                checkHospitalsOverlap(first_x_start, first_y_start, second_x_end, second_y_end) ||
                checkHospitalsOverlap(first_x_start, first_y_start, second_x_start, second_y_start) ||
                checkHospitalsOverlap(first_x_start, first_y_start, second_x_end, second_y_end) ||
                checkHospitalsOverlap(first_x_end, first_y_end, second_x_start, second_y_start) ||
                checkHospitalsOverlap(first_x_end, first_y_end, second_x_end, second_y_end) ||
                checkHospitalsOverlap(first_x_end, first_y_end, second_x_start, second_y_start) ||
                checkHospitalsOverlap(first_x_end, first_y_end, second_x_end, second_y_end)) {
            return null;
        }


        // TODO implement own linesIntersect method
        if (Line2D.linesIntersect(first_x_start, first_y_start, first_x_end, first_y_end,
                second_x_start, second_y_start, second_x_end, second_y_end) ) {
            // Line AB represented as a1x + b1y = c1
            double a1 = first_y_start - first_y_end;
            double b1 = first_x_end - first_x_start;
            double c1 = a1*(first_x_end) + b1*(first_y_end);

            // Line CD represented as a2x + b2y = c2
            double a2 = second_y_start - second_y_end;
            double b2 = second_x_end - second_x_start;
            double c2 = a2*(second_x_end)+ b2*(second_y_end);

            double determinant = a1*b2 - a2*b1;

            double x = (b2*c1 - b1*c2)/determinant;
            double y = (a1*c2 - a2*c1)/determinant;

            // TODO manipulate road - delete 2 old ones, create 4 new roads, calculate distances
            return new Intersection(DataBase.getHospitalsList().size() + DataBase.getIntersectionsList().size(), id1, id2, new Point2D.Double(x, y));
        }
        else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(((Road)o).getId() == this.id) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
