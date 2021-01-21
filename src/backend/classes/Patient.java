package classes;

import java.awt.geom.Point2D;

public class Patient implements Comparable<Patient>{
    private int id;
    private String name;
    private Point2D.Double cords;

    public Patient(int id, String name, Point2D.Double cords){
        this.id = id;
        this.name = name;
        this.cords = cords;
    }

    public int getId(){
        return id;
    }

    String getName(){
        return name;
    }

    void setX (double x){
        cords.setLocation(x, cords.getY());
    }
    void setY (double y){
        cords.setLocation(cords.getX(), cords.getX());
    }

    public double getX (){
        return cords.getX();
    }
    public double getY (){
        return cords.getY();
    }

    void setCords (Point2D.Double cords){
        cords.setLocation(cords);
    }

    Point2D.Double getCords (){
        return cords;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name + "\tCords: " + cords.getX() + "\t" + cords.getY();
    }

    @Override
    public int compareTo(Patient p) {
        return Integer.compare(this.getId(), p.getId());
    }

    @Override
    public boolean equals(Object o) {
        if(((Patient)o).getId() == this.id) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
