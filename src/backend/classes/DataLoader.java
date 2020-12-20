package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DataLoader {
    protected static String commentCharacter = "#";
    protected static int lineNumber = 1;
    protected static String loadedLine = "";

    protected static final String DATA_SPITER = " \\| ";

    protected void checkComment() {

    }

    protected boolean loadObject() {
        return true;
    }

    protected boolean loadData(String filePath) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8));
            while ((loadedLine = in.readLine()) != null) {
                if (loadedLine.startsWith(commentCharacter)) {
                    checkComment();
                } else {
                    if (loadObject() == false) {
                        return false;
                    }
                }
                lineNumber++;
            }
            in.close();
            return true;
        } catch (IOException e) {
            System.out.println("Plik \"" +filePath + "\" nie istnieje");
            return false;
        }
    }


    protected static int parseInt(String[] data, int dataIndex) throws NumberFormatException {
        try {
            return Integer.parseInt(data[dataIndex]);
        } catch (NumberFormatException e) {
            printParseIntError(data, dataIndex);
            throw e;
        }
    }

    protected static void printParseIntError(String[] data, int dataIndex){
        System.out.println("Błąd danych wejściowych w linii " + lineNumber + ":\n" + loadedLine);
        for(int i = 0; i < dataIndex; i++)
            for(int j = 0; j < data[i].length() + 3; j++)
                System.out.print(" ");

        System.out.println("|");
        System.out.println("Wymagana wartość liczbowa typu całkowitego");
    }
}
