package classes;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;

public class Country {
    private static ArrayList<Boundary> boundariesNodes = new ArrayList<Boundary>();

    public static void clearBoundaries() {
        boundariesNodes.clear();
    }
//
    static public ArrayList<Boundary> getBoundariesNodes(){
        return boundariesNodes;
    }

    static public void calculateBoundaries(ArrayList<Boundary> allBoundaries){
        ArrayList<Double> angles = new ArrayList<Double>();
        ArrayList<Boundary> goodBoundaries = new ArrayList<>();
        Collections.sort(allBoundaries, Boundary.SortByXAscThenYDesc);
        for (Boundary boundary: allBoundaries){
            if (goodBoundaries.size() == 0) {
                goodBoundaries.add(boundary);
            }
            else if (boundary.getCords().getY() >= goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY()){
                if (goodBoundaries.size() == 1){
                    angles.add(Math.atan2((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()), goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY()) );
                    goodBoundaries.add(boundary);
                }
                else{
                    Double tempAngle = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                    while (angles.get(angles.size() - 1) > tempAngle){
                        goodBoundaries.remove(goodBoundaries.size() - 1);
                        angles.remove(angles.size() - 1);
                        tempAngle = Math.atan2((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()), goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY()) ;
                        if (angles.size() == 0)
                                break;
                    }
                    goodBoundaries.add(boundary);
                    angles.add(tempAngle);
                }
            }
        }
        boundariesNodes.addAll(goodBoundaries);
        System.out.println(boundariesNodes.size());
        goodBoundaries = new ArrayList<>();
        angles = new ArrayList<Double>();
        Collections.sort(allBoundaries, Boundary.SortByYDesc);
        goodBoundaries.add(boundariesNodes.get(boundariesNodes.size() - 1));
        for (Boundary boundary: allBoundaries){
            if (boundary.getCords().getX() >= goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX()){
                if (goodBoundaries.size() == 1){
                    angles.add(Math.atan2((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()), goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY()) );
                    goodBoundaries.add(boundary);
                }
                else{
                    Double tempAngle = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                    while (angles.get(angles.size() - 1) < tempAngle){
                        goodBoundaries.remove(goodBoundaries.size() - 1);
                        angles.remove(angles.size() - 1);
                        tempAngle = Math.atan2((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()), goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY()) ;
                        if (angles.size() == 0)
                            break;
                    }
                    goodBoundaries.add(boundary);
                    angles.add(tempAngle);
                }
            }
        }
        goodBoundaries.remove(0);
        boundariesNodes.addAll(goodBoundaries);
        goodBoundaries = new ArrayList<>();
        angles = new ArrayList<Double>();
        Collections.sort(allBoundaries, Boundary.SortByXDesc);
        goodBoundaries.add(boundariesNodes.get(boundariesNodes.size() - 1));
        for (Boundary boundary: allBoundaries){
            if (boundary.getCords().getY() <= goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY()){
                if (goodBoundaries.size() == 1){
                    angles.add(Math.atan2((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()), goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY()) );
                    goodBoundaries.add(boundary);
                }
                else{
                    Double tempAngle = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                    while (angles.get(angles.size() - 1) < tempAngle){
                        goodBoundaries.remove(goodBoundaries.size() - 1);
                        angles.remove(angles.size() - 1);
                        tempAngle = Math.atan2((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()), goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY()) ;
                        if (angles.size() == 0)
                            break;
                    }
                    goodBoundaries.add(boundary);
                    angles.add(tempAngle);
                }
            }
        }
        goodBoundaries.remove(0);
        boundariesNodes.addAll(goodBoundaries);
        goodBoundaries = new ArrayList<>();
        angles = new ArrayList<Double>();
        Collections.sort(allBoundaries, Boundary.SortByYAsc);
        goodBoundaries.add(boundariesNodes.get(boundariesNodes.size() - 1));
        for (Boundary boundary: allBoundaries){
            if (boundary.getCords().getX() <= goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX()){
                if (goodBoundaries.size() == 1){
                    angles.add(Math.atan2((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()), goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY()) );
                    goodBoundaries.add(boundary);
                }
                else{
                    Double tempAngle = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                    while (angles.get(angles.size() - 1) < tempAngle){
                        goodBoundaries.remove(goodBoundaries.size() - 1);
                        angles.remove(angles.size() - 1);
                        tempAngle = Math.atan2((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()), goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY()) ;
                        if (angles.size() == 0)
                            break;
                    }
                    goodBoundaries.add(boundary);
                    angles.add(tempAngle);
                }
            }
        }
        goodBoundaries.remove(0);
        boundariesNodes.addAll(goodBoundaries);
    }
}
