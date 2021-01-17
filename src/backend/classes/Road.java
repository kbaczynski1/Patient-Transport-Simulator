package classes;

import javax.sound.sampled.Line;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;

public class Road  implements Comparable<Road>{
    private int id;
    private int firstHospitalId;
    private int secondHospitalId;
    private double value;
    private double distance;

    Road(int id, int firstHospitalId, int secondHospitalId, double distance){
        this.id = id;
        this.firstHospitalId = firstHospitalId;
        this.secondHospitalId = secondHospitalId;
        this.distance = distance;
//        this.calculateValue(this.getStartPoint().getX());
    }

    public void initValue() {
        this.calculateValue(this.getStartPoint().getX());
    }

    public int getId() {
        return id;
    }

    public Hospital getFirstHospital() {
        return DataBase.getHospital(firstHospitalId);
    }

    public int getFirstHospitalId() {
        return firstHospitalId;
    }

    public Hospital getSecondHospital() {
        return DataBase.getHospital(secondHospitalId);
    }

    public int getSecondHospitalId() {
        return secondHospitalId;
    }

    public double getDistance() {
        return distance;
    }

    public Point2D getStartPoint() {
        if (this.getFirstHospital().getCords().getX() <= this.getSecondHospital().getCords().getX()) {
            return this.getFirstHospital().getCords();
        } else {
            return getSecondHospital().getCords();
        }
    }

    public Point2D getEndPoint() {
        if (getFirstHospital().getCords().getX() <= getSecondHospital().getCords().getX()) {
            return getSecondHospital().getCords();
        } else {
            return getFirstHospital().getCords();
        }
    }

    //TODO understand what "value" means
    public void calculateValue(double value) {
        double x1 = this.getStartPoint().getX();
        double x2 = this.getEndPoint().getX();
        double y1 = this.getStartPoint().getY();
        double y2 = this.getEndPoint().getY();
        this.value = y1 + (((y2 - y1) / (x2 - x1)) * (value - x1));
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "[" + id + "] Distance: " + distance + "\n\t" + getFirstHospital().toString() + "\n\t" + getSecondHospital().toString();
    }

    @Override
    public int compareTo(Road road) {
        double current_x_cord = Math.min(getFirstHospital().getCords().getX(), getSecondHospital().getCords().getX());
        double road_x_cord = Math.min(road.getFirstHospital().getCords().getX(), road.getSecondHospital().getCords().getX());
        return Double.compare(current_x_cord, road_x_cord);
    }

    // TODO move it somewhere else
    public boolean checkHospitalsOverlap(double x1, double y1, double x2, double y2) {
        if(x1 == x2 && y1 == y2) {
            return true;
        }
        return false;
    }
    // TODO move it somewhere else or make it prettier
    public Intersection doesIntersect(Road road) {
        double first_x_start = this.getFirstHospital().getCords().getX();
        double first_y_start = this.getFirstHospital().getCords().getY();
        double first_x_end = this.getSecondHospital().getCords().getX();
        double first_y_end = this.getSecondHospital().getCords().getY();

        double second_x_start = road.getFirstHospital().getCords().getX();
        double second_y_start = road.getFirstHospital().getCords().getY();
        double second_x_end = road.getSecondHospital().getCords().getX();
        double second_y_end = road.getSecondHospital().getCords().getY();

        // TODO rewrite since it's ugly
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
            return new Intersection(-1, "X", new Point2D.Double(x, y));
        }
        else {
            return null;
        }
    }


}
