package classes;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;

public class Country {
    private static ArrayList<Boundary> boundariesNodes = new ArrayList<Boundary>();
//
    static public ArrayList<Boundary> getBoundariesNodes(){
        return boundariesNodes;
    }

    static public void calculateBoundaries(ArrayList<Boundary> allBoundaries){
        ArrayList<Double> slopes = new ArrayList<Double>();
        ArrayList<Boundary> goodBoundaries = new ArrayList<>();
        Collections.sort(allBoundaries, Boundary.SortByXAsc);
        for (Boundary boundary: allBoundaries){
            if (goodBoundaries.size() == 0)
                goodBoundaries.add(boundary);
            else if (boundary.getCords().getY() <= goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY()){
                if (goodBoundaries.size() == 1){
                    slopes.add((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()) );
                    goodBoundaries.add(boundary);
                }
                else{
                    Double tempSlope = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                    while (slopes.get(slopes.size() - 1) > tempSlope){
                        goodBoundaries.remove(goodBoundaries.size() - 1);
                        slopes.remove(slopes.size() - 1);
                        tempSlope = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                        if (slopes.size() == 0)
                                break;
                    }
                    goodBoundaries.add(boundary);
                    slopes.add(tempSlope);
                }
            }
        }
        boundariesNodes.addAll(goodBoundaries);
        goodBoundaries = new ArrayList<>();
        slopes = new ArrayList<Double>();
        Collections.sort(allBoundaries, Boundary.SortByYDesc);
        for (Boundary boundary: allBoundaries){
            if (goodBoundaries.size() == 0)
                goodBoundaries.add(boundary);
            else if (boundary.getCords().getX() >= goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX()){
                if (goodBoundaries.size() == 1){
                    slopes.add((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()) );
                    goodBoundaries.add(boundary);
                }
                else{
                    Double tempSlope = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                    while (slopes.get(slopes.size() - 1) > tempSlope){
                        goodBoundaries.remove(goodBoundaries.size() - 1);
                        slopes.remove(slopes.size() - 1);
                        tempSlope = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                        if (slopes.size() == 0)
                            break;
                    }
                    goodBoundaries.add(boundary);
                    slopes.add(tempSlope);
                }
            }
        }
        boundariesNodes.addAll(goodBoundaries);
        goodBoundaries = new ArrayList<>();
        slopes = new ArrayList<Double>();
        Collections.sort(allBoundaries, Boundary.SortByXDesc);
        for (Boundary boundary: allBoundaries){
            if (goodBoundaries.size() == 0)
                goodBoundaries.add(boundary);
            else if (boundary.getCords().getY() >= goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY()){
                if (goodBoundaries.size() == 1){
                    slopes.add((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()) );
                    goodBoundaries.add(boundary);
                }
                else{
                    Double tempSlope = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                    while (slopes.get(slopes.size() - 1) > tempSlope){
                        goodBoundaries.remove(goodBoundaries.size() - 1);
                        slopes.remove(slopes.size() - 1);
                        tempSlope = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                        if (slopes.size() == 0)
                            break;
                    }
                    goodBoundaries.add(boundary);
                    slopes.add(tempSlope);
                }
            }
        }
        boundariesNodes.addAll(goodBoundaries);
        goodBoundaries = new ArrayList<>();
        slopes = new ArrayList<Double>();
        Collections.sort(allBoundaries, Boundary.SortByYDesc);
        for (Boundary boundary: allBoundaries){
            if (goodBoundaries.size() == 0)
                goodBoundaries.add(boundary);
            else if (boundary.getCords().getX() <= goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX()){
                if (goodBoundaries.size() == 1){
                    slopes.add((goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX()) );
                    goodBoundaries.add(boundary);
                }
                else{
                    Double tempSlope = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                    while (slopes.get(slopes.size() - 1) > tempSlope){
                        goodBoundaries.remove(goodBoundaries.size() - 1);
                        slopes.remove(slopes.size() - 1);
                        tempSlope = (goodBoundaries.get(goodBoundaries.size() - 1).getCords().getY() - boundary.getCords().getY())/(goodBoundaries.get(goodBoundaries.size() - 1).getCords().getX() - boundary.getCords().getX());
                        if (slopes.size() == 0)
                            break;
                    }
                    goodBoundaries.add(boundary);
                    slopes.add(tempSlope);
                }
            }
        }
        boundariesNodes.addAll(goodBoundaries);
    }
}
