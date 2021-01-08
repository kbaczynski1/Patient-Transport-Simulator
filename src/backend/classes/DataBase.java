package classes;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DataBase {
    private static ArrayList<Hospital> hospitalsList = new ArrayList<Hospital>();
    private static ArrayList<Boundary> boundaryList = new ArrayList<Boundary>();
    private static ArrayList<Road> roadsList = new ArrayList<Road>();
    private static ArrayList<Patient> patientsLists = new ArrayList<Patient>();
    private static ArrayList<Intersection> intersectionsList = new ArrayList<Intersection>();


    //HOSPITAL

    public static void addHospital(Hospital hospital){
        hospitalsList.add(hospital);
    }

    public static Hospital getHospital(int id){
        return hospitalsList.stream()
                .filter(hospital -> id == hospital.getId())
                .findAny()
                .orElse(null);
    }

    public static ArrayList<Hospital> getHospitalsList(){
        return hospitalsList;
    }

    //BOUNDARY

    public static void addBoundary(Boundary boundary){
        boundaryList.add(boundary);
    }

    public static Boundary getBoundary(int id){
        return boundaryList.stream()
                .filter(boundary -> id == boundary.getId())
                .findAny()
                .orElse(null);
    }

    public static ArrayList<Boundary> getBoundaryList(){
        return boundaryList;
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

    //INTERSECTION

    public static void addIntesection(Intersection intersection){
        intersectionsList.add(intersection);
    }

    public static Patient getIntersection(int id){
        return patientsLists.stream()
                .filter(intersection -> id == intersection.getId())
                .findAny()
                .orElse(null);
    }

    public static ArrayList<Intersection> getIntersectionsList(){
        return intersectionsList;
    }

    //PRINTERS

    public static void printHospitals(){
        System.out.println("Lista Szpitali");
        System.out.println(hospitalsList.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
    }

    public static void printBoundary(){
        System.out.println("Lista Obiektów");
        System.out.println(boundaryList.stream()
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
}
