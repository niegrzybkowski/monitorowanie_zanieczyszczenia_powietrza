package pl.mini.pw.zanieczyszczenie.communicator;

import org.junit.Test;
import pl.mini.pw.zanieczyszczenie.communicator.pages.FindAll;
import pl.mini.pw.zanieczyszczenie.communicator.pages.Index;
import pl.mini.pw.zanieczyszczenie.communicator.pages.Readings;
import pl.mini.pw.zanieczyszczenie.communicator.pages.StationSensors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

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
    public void containsDzialoszyn() {
        FindAll findAll = basicParser.getFindAll();
        FindAll.Station dzialoszyn = new FindAll.Station(
                14,
                "Działoszyn",
                50.972167,
                14.941319,
                192,
                "Działoszyn",
                "Bogatynia",
                "zgorzelecki",
                "DOLNOŚLĄSKIE",
                "bez ulicy"
        );
        assertNotNull("FindAll container is null", findAll.getContainer());
        ArrayList<FindAll.Station> list = findAll.getContainer()
                .stream()
                .filter((x) -> x.getId()==14)
                .collect(Collectors.toCollection(ArrayList::new));
        assertEquals("Duplicate ID",1, list.size());
        assertEquals("Name mismatch", "Działoszyn", list.get(0).getStationName());
        assertEquals("FindAll.Station.equals() failure", dzialoszyn, list.get(0));
    }
    @Test
    public void testHash() {
        int sortedContainerHash = -2037789062;
        FindAll findAll = basicParser.getFindAll();
        var list = findAll.getContainer();
        list.sort(Comparator.comparingInt(FindAll.Station::getId));
        assertEquals("HashCode mismatch", sortedContainerHash, list.hashCode());
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