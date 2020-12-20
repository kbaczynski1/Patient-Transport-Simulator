package classes;

import java.awt.geom.Point2D;

public class Road {
    private int id;
    private int firstHospitalId;
    private int secondHospitalId;
    private double distance;

    Road(int id, int firstHospitalId, int secondHospitalId, double distance){
        this.id = id;
        this.firstHospitalId = firstHospitalId;
        this.secondHospitalId = secondHospitalId;
        this.distance = distance;
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

    @Override
    public String toString() {
        return "[" + id + "] Distance: " + distance + "\n\t" + getFirstHospital().toString() + "\n\t" + getSecondHospital().toString();
    }
}
