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
import static pl.mini.pw.zanieczyszczenie.communicator.TestUtilities.assertNotNullList;
import static pl.mini.pw.zanieczyszczenie.communicator.TestUtilities.assertSortedListHash;

public class BasicParserTest {
    static Parser basicParser = new BasicParser(TestUtilities::loadFromTestResources);

    @Test
    public void findAllNotNull() {
        FindAll findAll = basicParser.getFindAll();
        assertNotNull(findAll);
        assertNotNullList(findAll.getContainer());
    }

    @Test
    public void findAllContains() {
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
    public void findAllHash() {
        assertSortedListHash(-2037789062,
                basicParser.getFindAll().getContainer(),
                Comparator.comparingInt(FindAll.Station::getId));
    }

    @Test
    public void readingsNotNull() {
        Readings readings = basicParser.getReadings(92);
        assertNotNull(readings);
        assertNotNullList(readings.getObservations());
    }

    @Test
    public void readingsHash() {
//        assertSortedListHash(862275477,
//                basicParser.getReadings(92).getObservations(),
//                Comparator.comparing(Readings.Observation::getTime));
    }

    @Test
    public void stationSensorsNotNull() {
        StationSensors sensors = basicParser.getStationSensors(52);
        assertNotNull(sensors);
        assertNotNullList(sensors.getStationSensors());
    }

    @Test
    public void stationSensorsHash() {
        assertSortedListHash(-1701317268,
                basicParser.getStationSensors(52).getStationSensors(),
                Comparator.comparingInt(StationSensors.Sensor::getSensorID));
    }

    @Test
    public void indexNotNull() {
        Index index = basicParser.getIndex(52);
        assertNotNull(index);
        assertNotNullList(index.getIndexes());
    }

    @Test
    public void indexHash() {
        assertSortedListHash(-1312665777,
                basicParser.getIndex(52).getIndexes(),
                Comparator.comparing(Index.IndexData::getCalculationDate));
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
        var x = basicParser.getReadings(52);
        System.out.println(x.toString().replace("}, ", "}, \n"));
    }
}