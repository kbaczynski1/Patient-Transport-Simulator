package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathSearcher {
    private Patient patient;
    private Hospital hospital;
    private double[][] graph;

    public PathSearcher(Patient patient) {
        this.patient = patient;
        this.graph = DataBase.getGraph();
    }

    // check that the patient is on the territory of the country
    public boolean checkIfPatientIsInCountry() {
        // returns true if patient (patient.getX(), patient.getY()) is within the country
        List<Boundary> boundariesNodes = DataBase.getBoundariesList();
        boolean sideIsDefined = false;
        boolean side = false;
        for (int i = 0; i < boundariesNodes.size(); i++) {
            int j = i + 1;
            double x_i = boundariesNodes.get(i).getCords().getX();
            double y_i = boundariesNodes.get(i).getCords().getY();
            double x_j = boundariesNodes.get(j).getCords().getX();
            double y_j = boundariesNodes.get(j).getCords().getY();

            double h = (y_j - y_i) * (patient.getX() - x_i) - (x_j - x_i) * (patient.getY() - y_i);

            if (h == 0) {
                continue;
            } else {
                if (!sideIsDefined) {
                    if (h > 0) {
                        side = true;
                    }
                    sideIsDefined = true;
                } // point (patient) have to lie at the same side of all lines
                else if ((h > 0 && side == false) || (h < 0 && side == true)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Hospital getCurrentHospital() {
        return hospital;
    }

    // search the first nearest hospital
    public void searchFirstHospital() {
        List<Hospital> hospitals = DataBase.getHospitalsList();

        int id = 0;
        double distanceMin = Double.MAX_VALUE;
        double distance;
        for (int i = 0; i < hospitals.size(); i++) {
            // calculate the patient's distance to the hospital
            distance = Math.sqrt(Math.pow((hospitals.get(i).getCords().getX() - patient.getX()), 2)
                    + Math.pow((hospitals.get(i).getCords().getY() - patient.getY()), 2));
            if (distance < distanceMin) { // find and remember the shortest distance
                distanceMin = distance;
                id = i;
            }
        }
        DataBase.getNode(id).setVisited(true);
        hospital = hospitals.get(id); // assign the first nearest hospital

    }

    public Path[] checkNextNode(int id, double pathValue, Path[] bestPaths, ArrayList<Integer> path){
        for (int i = 0; i < graph[0].length; i++){
            if (graph[id-1][i] != 0){
                Node node = DataBase.getNode(i+1);
                if (! node.isVisited()) {
                    if (node.getCanStop()) {
                        pathValue += graph[id - 1][i];
                        path.add(i);
                        if (bestPaths[i].getValue() > pathValue) {
                            bestPaths[i].setValue(pathValue);
                            bestPaths[i].setNodesList(new ArrayList<>(path));
                        }
                        path.remove(path.size() - 1);
                        pathValue -= graph[id - 1][i];
                    } else {
                        node.setVisited(true);
                        path.add(i);
                        bestPaths = checkNextNode(i + 1, pathValue + graph[id - 1][i + 1], bestPaths, path);
                        path.remove(path.size() - 1);
                        node.setVisited(false);
                    }
                }
            }
        }
        return bestPaths;
    }

    // search the next nearest hospital
    public ArrayList<Integer> searchNextHospital() {
        Path[] bestPaths = new Path[graph.length];
        ArrayList<Integer> path = new ArrayList<>();
        bestPaths = checkNextNode(hospital.getId(), 0.0, bestPaths, path);
        double minVal = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int i = 0; i < bestPaths.length; i++) {
            if (bestPaths[i].getValue() <= minVal) {
                minVal = bestPaths[i].getValue();
                index = i;
            }
        }
        if (minVal < Double.POSITIVE_INFINITY) {
            return bestPaths[index].getNodesList();
        } else {
            return null;
        }
    }

















//        List<Hospital> hospitals = DataBase.getHospitalsList();
//        List<Node> nodes = DataBase.getNodesList();
//
//        // check that the hospital has direct connections to unvisited hospitals
//        // check that the hospital has direct connections to intersections or hospitals visited
//        List<Road> roads = DataBase.getRoadsList();
//        boolean connectionWithUnvisitedHospitals = false;
//        boolean connectionWithVisitedNodes = false;
//        double minDirectDistance = Double.MAX_VALUE;
//        int idOfMinDirectDistance = 0;
//        for (int i = 0; i < nodes.size(); i++) {
//            if (nodes.get(i).getId() != hospital.getId()) {
//                for (Road road : roads) {
//                    if ((road.getFirstNodeId() == hospital.getId() &&
//                            road.getSecondNodeId() == nodes.get(i).getId()) ||
//                            (road.getFirstNodeId() == nodes.get(i).getId() &&
//                                    road.getSecondNodeId() == hospital.getId())) {
//                        if (!nodes.get(i).isVisited()) {
//                            connectionWithUnvisitedHospitals = true;
//                            double distance = road.getDistance();
//                            if (distance < minDirectDistance) {
//                                minDirectDistance = distance;
//                                idOfMinDirectDistance = i;
//                            }
//                        } else {
//                            connectionWithVisitedNodes = true;
//                        }
//                    }
//                }
//
//            }
//        }
//        if (!connectionWithUnvisitedHospitals && !connectionWithVisitedNodes) {
//            // the end of patient transport, leave him in the queue at the current hospital
//            return;
//        } else if (connectionWithUnvisitedHospitals && !connectionWithVisitedNodes) {
//            // go to the nearest directly connected hospital
//            hospital = hospitals.get(idOfMinDirectDistance);
//
//        } else {
//            // find the shortest path in a graph consisting of intersections, hospitals visited
//            // and hospitals not visited that have no direct connection to the current hospital
//
//        }


}
