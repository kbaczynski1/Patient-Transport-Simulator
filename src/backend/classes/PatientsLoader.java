package classes;

import interfaces.Loader;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PatientsLoader implements Loader {
    private enum LoadingDataType {
        NONE, PATIENT;
    }

    private static LoadingDataType loadingMode = LoadingDataType.NONE;
    private static String commentCharacter = "#";

    private static final String DATA_SPITER = " \\| ";

    private static final int PATIENT_DATA_SIZE = 3;

    private static int line_num = 1;

    @Override
    public void loadDataFromFile(String filePath) {

    }

    @Override
    public void vaildateData(String filePath) {

    }

    public static void loadData(String filePath) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith(commentCharacter)) {
                    checkComment(line);
                } else {
                    switch (loadingMode) {
                        case NONE:
                            break;
                        case PATIENT:
                            loadPatient(line);
                            break;
                    }
                }
                line_num++;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Plik \"" +filePath + "\" nie istnieje");
            System.exit(0);
            //e.printStackTrace();
        }
    }

    private static void checkComment(String line) {
        if (line.startsWith("# Pacjenci")) {
            loadingMode = LoadingDataType.PATIENT;
        } else {
            // Other comment
        }
    }

    private static void loadPatient(String line) {
        String[] patientData = line.split(DATA_SPITER);
        if (patientData.length == PATIENT_DATA_SIZE) {
            int patientId = 0;
            double patientX = 0.0;
            double patientY = 0.0;

            try {
                patientId = Integer.parseInt(patientData[0]);
            } catch (NumberFormatException e) {
                System.out.println("Błąd danych wejściowych w linii " + line_num + ":\n" + line);
                System.out.println("|");
                System.out.println("Wymaga wartość liczbowa typu całkowitego");
                System.exit(0);
            }

            try {
                patientX = Integer.parseInt(patientData[1]);
            } catch (NumberFormatException e) {
                System.out.println("Błąd danych wejściowych w linii " + line_num + ":\n" + line);
                for(int i = 0; i < patientData[0].length() + 6; i++)
                    System.out.print(" ");
                System.out.println("|");
                System.out.println("Wymaga wartość liczbowa typu całkowitego");
                System.exit(0);
            }

            try {
                patientY = Integer.parseInt(patientData[2]);
            } catch (NumberFormatException e) {
                System.out.println("Błąd danych wejściowych w linii " + line_num + ":\n" + line);
                for(int i = 0; i < patientData[0].length() + patientData[1].length() + 6; i++)
                    System.out.print(" ");
                System.out.println("|");
                System.out.println("Wymaga wartość liczbowa typu całkowitego");
                System.exit(0);
            }

            DataBase.addPatient(new Patient(patientId, "", new Point2D.Double(patientX, patientY)));

        } else {
            System.out.println("Niewłaściwa liczba danych dla producenta w linii " + line_num +":\n" + line);
            System.exit(0);
        }
    }
}
