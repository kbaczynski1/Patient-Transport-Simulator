package classes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataLoaderTest {

    private DataLoader dataLoader;

    @Before
    public void setUp() {
        dataLoader = new DataLoader();
    }

    @Test
    public void should_returnFalse_when_inputFileDoesNotExist() {
        // given
        String filepath = "nonExistentFile.txt";

        // when
        boolean fileExists = dataLoader.loadData(filepath);

        // then
        assertFalse(fileExists);
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_hospitalIDIsNotInteger() {
        // given
        String[] data = {"1.0", "Szpital Wojewódzki nr 997", "10", "10", "1000", "100"};
        int dataIndex = 0;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_BedsAmountIsNotInteger() {
        // given
        String[] data = {"1", "Szpital Wojewódzki nr 997", "10", "10", "1000.0", "100"};
        int dataIndex = 4;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_freeBedsAmountIsNotInteger() {
        // given
        String[] data = {"1", "Szpital Wojewódzki nr 997", "10", "10", "1000", "100.0"};
        int dataIndex = 5;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_xCoordinateOfHospitalIsNotNumber() {
        // given
        String[] data = {"1", "Szpital Wojewódzki nr 997", "10ab", "10", "1000", "100.0"};
        int dataIndex = 2;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_yCoordinateOfHospitalIsNotNumber() {
        // given
        String[] data = {"1", "Szpital Wojewódzki nr 997", "10", "10cd", "1000", "100"};
        int dataIndex = 3;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_monumentIDIsNotInteger() {
        // given
        String[] data = {"2.0", "Pomnik Fryderyka Chopina", "110", "55"};
        int dataIndex = 0;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_xCoordinateOfMonumentIsNotNumber() {
        // given
        String[] data = {"2", "Pomnik Fryderyka Chopina", "110ef", "55"};
        int dataIndex = 2;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_yCoordinateOfMonumentIsNotNumber() {
        // given
        String[] data = {"2", "Pomnik Fryderyka Chopina", "110", "55gh"};
        int dataIndex = 3;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_roadIDIsNotInteger() {
        // given
        String[] data = {"3.0", "1", "5", "800"};
        int dataIndex = 0;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_firstNodeIDOfRoadIsNotInteger() {
        // given
        String[] data = {"3", "1.0", "5", "800"};
        int dataIndex = 1;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_secondNodeIDOfRoadIsNotInteger() {
        // given
        String[] data = {"3", "1", "5.0", "800"};
        int dataIndex = 2;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_when_distanceOfRoadIsNotNumber() {
        // given
        String[] data = {"3", "1", "5.0", "800ijk"};
        int dataIndex = 3;

        // when
        DataLoader.parseInt(data, dataIndex);

        // then
        assert false;
    }

}
