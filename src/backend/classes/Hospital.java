package classes;

import java.awt.geom.Point2D;

public class Hospital extends Boundary{
    private int bedsAmount;
    private int freeBedsAmount;

    public Hospital(int id, String name, Point2D.Double cords, int bedsAmount, int freeBedsAmount){
        super(id, name, cords);
        this.bedsAmount = bedsAmount;
        this.freeBedsAmount = freeBedsAmount;
    }

    public void setFreeBedsAmount(int freeBedsAmount){
        this.freeBedsAmount = freeBedsAmount;
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
