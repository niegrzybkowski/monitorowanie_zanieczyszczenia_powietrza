package pl.mini.pw.zanieczyszczenie.communicator;

import org.junit.Test;
import pl.mini.pw.zanieczyszczenie.communicator.pages.FindAll;
import pl.mini.pw.zanieczyszczenie.communicator.pages.Index;
import pl.mini.pw.zanieczyszczenie.communicator.pages.Readings;
import pl.mini.pw.zanieczyszczenie.communicator.pages.StationSensors;

import static org.junit.Assert.*;
import static pl.mini.pw.zanieczyszczenie.communicator.TestUtilities.notNullListHelper;

public class BasicParserTest {
    static Parser basicParser = new BasicParser(TestUtilities::loadFromTestResources);



    @Test
    public void parseFindAll() {
        FindAll findAll = basicParser.getFindAll();
        assertNotNull(findAll);
        notNullListHelper(findAll.getContainer());
    }

    @Test
    public void parseReadings() {
        Readings readings = basicParser.getReadings(92);
        assertNotNull(readings);
        notNullListHelper(readings.getObservations());
    }

    @Test
    public void parseStationSensorsNotNull() {
        StationSensors sensors = basicParser.getStationSensors(52);
        assertNotNull(sensors);
        notNullListHelper(sensors.getStationSensors());
    }

    @Test
    public void parseGetIndexNotNull() {
        Index index = basicParser.getIndex(52);
        assertNotNull(index);
        notNullListHelper(index.getIndexes());
    }
}