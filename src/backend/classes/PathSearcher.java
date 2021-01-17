package classes;

import java.util.ArrayList;
import java.util.List;

public class PathSearcher {
    private Patient patient;
    private Hospital hospital;

    public PathSearcher(Patient patient) {
        this.patient = patient;
    }

    // check that the patient is on the territory of the country
    public boolean checkIfPatientIsInCountry() {
        // returns true if patient (patient.getX(), patient.getY()) is within the country
        List<Boundary> boundariesNodes = DataBase.getBoundaryList();
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
        hospital = hospitals.get(id); // assign the first nearest hospital
    }

    // search the next nearest hospital
    public void searchNextHospital() {
        List<Hospital> hospitals = DataBase.getHospitalsList();
        List<Intersection> intersections = DataBase.getIntersectionsList();
        int n = hospitals.size() + intersections.size();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i <= n; i++) { // creating Node objects and list of nodes
            if (i < hospitals.size()) {
                Node node = new Node(hospitals.get(i).getId(), false, false);
                nodes.add(node);
            } else {
                Node node = new Node(intersections.get(i).getId(), true, false);
                nodes.add(node);
            }
        }

        // check that the hospital has direct connections to unvisited hospitals
        // check that the hospital has direct connections to intersections or hospitals visited
        List<Road> roads = DataBase.getRoadsList();
        boolean connectionWithUnvisitedHospitals = false;
        boolean connectionWithVisitedNodes = false;
        double minDirectDistance = Double.MAX_VALUE;
        int idOfMinDirectDistance = 0;
        for (int i = 0; i < n; i++) {
            if (nodes.get(i).getId() != hospital.getId()) {
                for (Road road : roads) {
                    if ((road.getFirstHospital().getId() == hospital.getId() &&
                            road.getSecondHospital().getId() == nodes.get(i).getId()) ||
                            (road.getFirstHospital().getId() == nodes.get(i).getId() &&
                                    road.getSecondHospital().getId() == hospital.getId())) {
                        if (!nodes.get(i).isVisited()) {
                            connectionWithUnvisitedHospitals = true;
                            nodes.get(i).setDirect(true);
                            double distance = road.getDistance();
                            if (distance < minDirectDistance) {
                                minDirectDistance = distance;
                                idOfMinDirectDistance = i;
                            }
                        } else {
                            connectionWithVisitedNodes = true;
                            nodes.get(i).setDirect(true);
                        }
                    }
                }

            }
        }
        if (!connectionWithUnvisitedHospitals && !connectionWithVisitedNodes) {
            // the end of patient transport, leave him in the queue at the current hospital
            return;
        } else if (connectionWithUnvisitedHospitals && !connectionWithVisitedNodes) {
            // go to the nearest directly connected hospital
            hospital = hospitals.get(idOfMinDirectDistance);

        } else {
            // find the shortest path in a graph consisting of intersections, hospitals visited
            // and hospitals not visited that have no direct connection to the current hospital

        }

    }
}
