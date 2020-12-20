package classes;

import java.awt.geom.Point2D;

public class Hospital {
    private int id;
    private String name;
    private Point2D.Double cords;
    private int bedsAmount;
    private int freeBedsAmount;

    public Hospital(int id, String name, Point2D.Double cords, int bedsAmount, int freeBedsAmount){
        this.id = id;
        this.name = name;
        this.cords = cords;
        this.bedsAmount = bedsAmount;
        this.freeBedsAmount = freeBedsAmount;
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

    public int getBedsAmount() {
        return bedsAmount;
    }

    public int getFreeBedsAmount() {
        return freeBedsAmount;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name + "\tCords: " + cords.getX() + "\t" + cords.getY() + "\tBeds: " + (bedsAmount - freeBedsAmount) + "/"+ bedsAmount;
    }

}
