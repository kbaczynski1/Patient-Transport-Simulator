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
            DataBase.addTerminalMessage("Plik \"" +filePath + "\" nie istnieje");
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

    protected static double parseDouble(String[] data, int dataIndex) throws NumberFormatException {
        try {
            return Double.parseDouble(data[dataIndex]);
        } catch (NumberFormatException e) {
            printParseDoubleError(data, dataIndex);
            throw e;
        }
    }

    protected static void printParseIntError(String[] data, int dataIndex){
        DataBase.addTerminalMessage("Błąd danych wejściowych, pole: \""+ data[dataIndex] + "\" w linii "
                + lineNumber + ":\n" + loadedLine);
        DataBase.addTerminalMessage("Wymagana wartość liczbowa typu całkowitego");
    }

    protected static void printParseDoubleError(String[] data, int dataIndex){
        DataBase.addTerminalMessage("Błąd danych wejściowych, pole: \""+ data[dataIndex] + "\" w linii "
                + lineNumber + ":\n" + loadedLine);
        DataBase.addTerminalMessage("Wymagana wartość liczbowa");
    }
}
