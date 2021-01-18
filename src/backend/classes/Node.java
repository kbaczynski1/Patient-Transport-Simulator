package classes;

import java.awt.geom.Point2D;

public class Node {
    private int id;
    private Point2D.Double cords;

    Node(int ID, Point2D.Double cor){
        id = ID;
        cords = cor;
    }

    public int getId(){ return id;}
    public Point2D.Double getCords(){ return cords;}
}
