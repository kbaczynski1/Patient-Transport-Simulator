package classes;

import java.awt.geom.Point2D;

public class Boundary {
    private int id;
    private String name;
    private Point2D.Double cords;

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
}
