package classes;

import java.awt.geom.Point2D;

public class Boundary {
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
}
