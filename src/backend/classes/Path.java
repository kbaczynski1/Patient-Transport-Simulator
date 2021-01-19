package classes;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class Path {
    private double value;
    private ArrayList<Integer> nodesList;

    Path(){
        this.value = Double.POSITIVE_INFINITY;
        nodesList = new ArrayList<>();
    }

    public double getValue(){ return value;}

    public void setValue(double value) {
        this.value = value;
    }

    public ArrayList<Integer> getNodesList(){ return nodesList;}

    public void setNodesList(ArrayList<Integer> nodesList) {
        this.nodesList = nodesList;
    }
}
