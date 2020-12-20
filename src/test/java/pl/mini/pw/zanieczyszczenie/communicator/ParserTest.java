package pl.mini.pw.zanieczyszczenie.communicator;

import org.junit.Test;
import pl.mini.pw.zanieczyszczenie.communicator.APIPage.FindAll;
import pl.mini.pw.zanieczyszczenie.communicator.APIPage.Index;
import pl.mini.pw.zanieczyszczenie.communicator.APIPage.Readings;
import pl.mini.pw.zanieczyszczenie.communicator.APIPage.StationSensors;
import pl.mini.pw.zanieczyszczenie.data.testData.Read;

import static org.junit.Assert.*;
public class ParserTest {
    Parser parser = new Parser();
    Read reader = new Read();
    String dateOfData = reader.getAvailableDates()[0];

    @Test
    public void parseFindAll() {
        String data = reader.readFindAll(dateOfData);

        Object findAll = parser.parseFindAll(data);

        assertTrue(findAll instanceof FindAll);
    }

    @Test
    public void parseReadings() {
        String data = reader.readReadings(dateOfData, 92);

        Object readings = parser.parseReadings(data);

        assertTrue(readings instanceof Readings);
    }

    @Test
    public void parseStationSensors() {
        String data = reader.readStationSensors(dateOfData, 52);

        Object stationSensors = parser.parseStationSensors(data);

        assertTrue(stationSensors instanceof StationSensors);
    }

    @Test
    public void parseGetIndex() {
        String data = reader.readIndex(dateOfData, 52);

        Object index = parser.parseGetIndex(data);

        assertTrue(index instanceof Index);
    }
}