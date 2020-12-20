package pl.mini.pw.zanieczyszczenie.communicator;

import org.junit.Test;
import pl.mini.pw.zanieczyszczenie.communicator.pages.FindAll;
import pl.mini.pw.zanieczyszczenie.communicator.pages.Index;
import pl.mini.pw.zanieczyszczenie.communicator.pages.Readings;
import pl.mini.pw.zanieczyszczenie.communicator.pages.StationSensors;
import pl.mini.pw.zanieczyszczenie.data.testData.Read;

import static org.junit.Assert.*;
public class BasicParserTest {
    BasicParser basicParser = new BasicParser();
    Read reader = new Read();
    String dateOfData = reader.getAvailableDates()[0];

    @Test
    public void parseFindAll() {
        String data = reader.readFindAll(dateOfData);

        Object findAll = basicParser.parseFindAll(data);

        assertTrue(findAll instanceof FindAll);
    }

    @Test
    public void parseReadings() {
        String data = reader.readReadings(dateOfData, 92);

        Object readings = basicParser.parseReadings(data);

        assertTrue(readings instanceof Readings);
    }

    @Test
    public void parseStationSensors() {
        String data = reader.readStationSensors(dateOfData, 52);

        Object stationSensors = basicParser.parseStationSensors(data);

        assertTrue(stationSensors instanceof StationSensors);
    }

    @Test
    public void parseGetIndex() {
        String data = reader.readIndex(dateOfData, 52);

        Object index = basicParser.parseGetIndex(data);

        assertTrue(index instanceof Index);
    }
}