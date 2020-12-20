package classes;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DataBase {
    private static ArrayList<Hospital> hospitalsList = new ArrayList<Hospital>();
    private static ArrayList<Patient> patientsLists = new ArrayList<Patient>();

    public static void addHospital(Hospital hospital){
        hospitalsList.add(hospital);
    }

    public static Hospital getHospital(int id){
        return hospitalsList.stream()
                .filter(patient -> id == patient.getId())
                .findAny()
                .orElse(null);
    }

    public static ArrayList<Hospital> getHospitalsList(){
        return hospitalsList;
    }

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

    public static void printHospitals(){
        System.out.println("Lista Szpitali");
        System.out.println(hospitalsList.stream()
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
