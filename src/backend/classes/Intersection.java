package classes;

import java.awt.geom.Point2D;

// TODO there is something wrong with inheritances
// TODO does it really need to have id and name in it? -> maybe rewrite boundary and implement
//      id and name in Monument and Hospital
// TODO Intersection needs to be with Hospitals in case of creating new roads and creating graph
public class Intersection{
    int id;
    int road1Id;
    int road2Id;
    Point2D.Double cords;
    Intersection(int id, int r1Id, int r2Id, Point2D.Double c) {
        this.id = id;
        this.road1Id = r1Id;
        this.road2Id = r2Id;
        this.cords = c;
    }

    public Point2D.Double getCords() {
        return cords;
    }

    public String toString() {
        return "[" + road1Id + "][" + road2Id + "]\tCords: " + cords.getX() + "\t" + cords.getY();
    }

    public int getRoad1Id() {
        return road1Id;
    }

    public int getRoad2Id() {
        return road2Id;
    }

    public int getId(){return id;}

}
