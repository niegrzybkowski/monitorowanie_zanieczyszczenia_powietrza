package pl.mini.pw.zanieczyszczenie.communicator;

import org.junit.Test;
import pl.mini.pw.zanieczyszczenie.communicator.pages.FindAll;
import pl.mini.pw.zanieczyszczenie.communicator.pages.Index;
import pl.mini.pw.zanieczyszczenie.communicator.pages.Readings;
import pl.mini.pw.zanieczyszczenie.communicator.pages.StationSensors;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static pl.mini.pw.zanieczyszczenie.communicator.TestUtilities.notNullListHelper;

public class BasicParserTest {
    static Parser basicParser = new BasicParser(TestUtilities::loadFromTestResources);

    @Test
    public void parseFindAllNotNull() {
        FindAll findAll = basicParser.getFindAll();
        assertNotNull(findAll);
        notNullListHelper(findAll.getContainer());
    }

    @Test
    public void parseReadingsNotNull() {
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

    @Test
    public void timestampCheck() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime created = basicParser.getFindAll().getUpdateTime();
        LocalDateTime end = LocalDateTime.now();
        assertTrue(created.isAfter(start) && created.isBefore(end));
    }

    @Test
    public void printIndex() {
        var x = basicParser.getIndex(14);
        System.out.println(x.toString().replace("}, ","},\n"));
    }

    @Test
    public void printReadings() {
        var x = basicParser.getIndex(14);
        System.out.println(x.toString().replace("}, ", "}, \n"));
    }
}