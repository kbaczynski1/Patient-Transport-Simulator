package classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DataBase {
    private static ArrayList<Hospital> hospitalsList = new ArrayList<Hospital>();
    private static ArrayList<Monument> monumentsList = new ArrayList<Monument>();
    private static ArrayList<Road> roadsList = new ArrayList<Road>();
    private static ArrayList<Patient> patientsLists = new ArrayList<Patient>();
    private static ArrayList<Boundary> boundariesList = new ArrayList<>();
    private static ArrayList<Intersection> intersectionsList = new ArrayList<>();
    private  static  ArrayList<Node> nodesList = new ArrayList<>();
    private static double[][] graph;

    private static ArrayList<String> terminalMessagesList = new ArrayList<>();


    //HOSPITAL
    public static void setGraph(){
        graph = new double[nodesList.size()][nodesList.size()];
        for (double[] row: graph)
            Arrays.fill(row, 0.0);
        for (Road road : roadsList){
            graph[road.getSecondNodeId() - 1 ][road.getFirstNodeId() - 1] = road.getDistance();
            graph[road.getFirstNodeId() - 1 ][road.getSecondNodeId() - 1] = road.getDistance();
        }
    }

    public static double[][] getGraph(){
        return graph;
    }

    public static void addHospital(Hospital hospital){
        hospitalsList.add(hospital);
        nodesList.add(new Node(hospital.getId(), hospital.getCords(), false, (hospital.getFreeBedsAmount() != 0)));
    }

    public static Hospital getHospital(int id){
        return hospitalsList.stream()
                .filter(hospital -> id == hospital.getId())
                .findAny()
                .orElse(null);
    }

    public static Node getNode(int id){
        return nodesList.stream()
                .filter(node -> id == node.getId())
                .findAny()
                .orElse(null);
    }

    public static void checkIfCanStop(){
        for (Hospital hospital : hospitalsList){
            if (hospital.getFreeBedsAmount() != 0)
                getNode(hospital.getId()).setCanStop(true);
        }
    }

    public static ArrayList<Node> getNodesList() {
        return nodesList;
    }

    public static ArrayList<Hospital> getHospitalsList(){
        return hospitalsList;
    }

    //MONUMENT

    public static void addMonument(Monument monument){
        monumentsList.add(monument);
    }

    public static Monument getMonument(int id){
        return monumentsList.stream()
                .filter(monument -> id == monument.getId())
                .findAny()
                .orElse(null);
    }

    public static ArrayList<Monument> getMonumentsList(){
        return monumentsList;
    }

    //ROAD

    public static void addRoad(Road road){
        roadsList.add(road);
    }

    public static Road getRoad(int id){
        return roadsList.stream()
                .filter(road -> id == road.getId())
                .findAny()
                .orElse(null);
    }

    public static ArrayList<Road> getRoadsList(){
        return roadsList;
    }

    //PATIENT

    public static void addPatient(Patient patient){
        patientsLists.add(patient);
    }

    public static Patient getPatient(int id){
        return patientsLists.stream()
                .filter(patient -> id == patient.getId())
                .findAny()
                .orElse(null);
    }

    public static ArrayList<Patient> getPatientsList(){
        return patientsLists;
    }

    //BOUNDARIES

    public static void addBoundary(Boundary boundary){
        boundariesList.add(boundary);
    }

    public static Boundary getBoundary(int id){
        return boundariesList.stream()
                .filter(boundary -> id == boundary.getId())
                .findAny()
                .orElse(null);
    }
    //INTERSECTION

    public static void addIntesection(ArrayList<Intersection> intersection){
        intersectionsList.addAll(intersection);
    }

    public static void divideRoads(){
        for (Intersection inter : intersectionsList) {
            Road road = DataBase.getRoad(inter.getRoad1Id());
            if (road.getFirstNode().getCords().getX() - road.getSecondNode().getCords().getX() != 0){
                roadsList.add(new Road(roadsList.size() + 1, road.getFirstNodeId(), inter.getId(), road.getDistance() * (road.getFirstNode().getCords().getX() - inter.getCords().getX()) / (double) (road.getFirstNode().getCords().getX() - road.getSecondNode().getCords().getX())));
                roadsList.add(new Road(roadsList.size() + 1, road.getSecondNodeId(), inter.getId(), road.getDistance() * (road.getSecondNode().getCords().getX() - inter.getCords().getX()) / (double) road.getSecondNode().getCords().getX() - road.getFirstNode().getCords().getX()));
            }
            else {
                roadsList.add(new Road(roadsList.size() + 1, road.getFirstNodeId(), inter.getId(), road.getDistance() * (road.getFirstNode().getCords().getY() - inter.getCords().getY()) / (double) (road.getFirstNode().getCords().getY() - road.getSecondNode().getCords().getY())));
                roadsList.add(new Road(roadsList.size() + 1, road.getSecondNodeId(), inter.getId(), road.getDistance() * (road.getSecondNode().getCords().getY() - inter.getCords().getY()) / (double) road.getSecondNode().getCords().getY() - road.getFirstNode().getCords().getY()));
            }
            road = DataBase.getRoad(inter.getRoad2Id());
            if (road.getFirstNode().getCords().getX() - road.getSecondNode().getCords().getX() != 0){
                roadsList.add(new Road(roadsList.size() + 1, road.getFirstNodeId(), inter.getId(), road.getDistance() * (road.getFirstNode().getCords().getX() - inter.getCords().getX()) / (double) (road.getFirstNode().getCords().getX() - road.getSecondNode().getCords().getX())));
                roadsList.add(new Road(roadsList.size() + 1, road.getSecondNodeId(), inter.getId(), road.getDistance() * (road.getSecondNode().getCords().getX() - inter.getCords().getX()) / (double) road.getSecondNode().getCords().getX() - road.getFirstNode().getCords().getX()));
            }
            else {
                roadsList.add(new Road(roadsList.size() + 1, road.getFirstNodeId(), inter.getId(), road.getDistance() * (road.getFirstNode().getCords().getY() - inter.getCords().getY()) / (double) (road.getFirstNode().getCords().getY() - road.getSecondNode().getCords().getY())));
                roadsList.add(new Road(roadsList.size() + 1, road.getSecondNodeId(), inter.getId(), road.getDistance() * (road.getSecondNode().getCords().getY() - inter.getCords().getY()) / (double) road.getSecondNode().getCords().getY() - road.getFirstNode().getCords().getY()));
            }
        }
        for (Intersection inter : intersectionsList){
            roadsList.remove(getRoad(inter.getRoad1Id()));
            roadsList.remove(getRoad(inter.getRoad2Id()));
        }
        addIntersectionToNodes();
    }

    private static void addIntersectionToNodes(){
        for (Intersection inter : intersectionsList){
            nodesList.add(new Node(inter.getId(), inter.getCords(), false, false));
        }
    }


    public static Patient getIntersection(int id){
        return patientsLists.stream()
                .filter(intersection -> id == intersection.getId())
                .findAny()
                .orElse(null);
    }

    public static ArrayList<Boundary> getBoundariesList() {
        return boundariesList;
    }

    public static ArrayList<Intersection> getIntersectionsList(){
        return intersectionsList;
    }

    //TERMINAL

    public static void addTerminalMessage(String message){
        terminalMessagesList.add(message);
    }

    public static ArrayList<String> getTerminalMessagesList(){
        return terminalMessagesList;
    }

    //PRINTERS

    public static void printHospitals(){
        System.out.println("Lista Szpitali");
        System.out.println(hospitalsList.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
    }

    public static void printMonuments(){
        System.out.println("Lista Obiektów");
        System.out.println(monumentsList.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
    }

    public static void printRoads(){
        System.out.println("Lista Dróg");
        System.out.println(roadsList.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
    }

    public static void printPatiens(){
        System.out.println("Lista Pacjentów");
        System.out.println(patientsLists.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
    }

    public static void clearHospitals() {
        hospitalsList.clear();
    }

    public static void clearMonuments() {
        monumentsList.clear();
    }

    public static void clearRoads() {
        roadsList.clear();
    }

    public static void clearIntersections() {
        intersectionsList.clear();
    }

    public static void clearPatients() {
        patientsLists.clear();
    }

    public static void clearBoundaries() {
        boundariesList.clear();
    }

    public static void clearNodes() {
        nodesList.clear();
    }
}
