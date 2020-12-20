package classes;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DataBase {
    private static ArrayList<Patient> patientList = new ArrayList<Patient>();

    public static void addPatient(Patient patient){
        patientList.add(patient);
    }

    public static Patient getPatient(int id){
        return patientList.stream()
                .filter(patient -> id == patient.getId())
                .findAny()
                .orElse(null);
    }

    public static ArrayList<Patient> getPatientsList(){
        return patientList;
    }

    public static void printPatiens(){
        System.out.println("Lista Pacjentów");
        System.out.println(patientList.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
    }
}
