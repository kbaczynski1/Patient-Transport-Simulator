package classes;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class Boundary implements Comparable<Boundary>{
    protected int id;
    protected String name;
    protected Point2D.Double cords;

    Boundary(int id, String name, Point2D.Double cords){
        this.id = id;
        this.name = name;
        this.cords = cords;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Point2D.Double getCords() {
        return cords;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name + "\tCords: " + cords.getX() + "\t" + cords.getY();
    }

    public static Comparator<Boundary> SortByXAscThenYDesc = new Comparator<Boundary>(){
        public int compare(Boundary one, Boundary two) {
            if (Double.compare(one.getCords().getX(), two.getCords().getX()) == 0)
                return Double.compare(two.getCords().getY(), one.getCords().getY());
            else
                return Double.compare(one.getCords().getX(), two.getCords().getX());
        }
    };

    public static Comparator<Boundary> SortByYAsc = new Comparator<Boundary>() {
        public int compare(Boundary one, Boundary two) {
            return Double.compare(one.getCords().getY(), two.getCords().getY());
        }
    };

    public static Comparator<Boundary> SortByXDesc = new Comparator<Boundary>(){
        public int compare(Boundary one, Boundary two) {
            return Double.compare(two.getCords().getX(), one.getCords().getX());
        }
    };

    public static Comparator<Boundary> SortByYDesc = new Comparator<Boundary>() {
        public int compare(Boundary one, Boundary two) {
            return Double.compare(two.getCords().getY(), one.getCords().getY());
        }
    };


    @Override
     public int compareTo(Boundary b) {
        return Integer.compare(this.getId(), b.getId());
    }

    @Override
    public boolean equals(Object o) {
        if(((Boundary)o).getId() == this.id) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}