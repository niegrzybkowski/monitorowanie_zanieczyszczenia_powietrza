package pl.mini.pw.zanieczyszczenie.communicator;

import org.junit.Test;
import pl.mini.pw.zanieczyszczenie.communicator.pages.FindAllPage;
import pl.mini.pw.zanieczyszczenie.communicator.pages.IndexPage;
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
        FindAllPage findAll = basicParser.getFindAll();
        assertNotNull(findAll);
        assertNotNullList(findAll.getContainer());
    }

    @Test
    public void findAllContains() {
        FindAllPage findAll = basicParser.getFindAll();
        FindAllPage.Station dzialoszyn = new FindAllPage.Station(
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
        ArrayList<FindAllPage.Station> list = findAll.getContainer()
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
                Comparator.comparingInt(FindAllPage.Station::getId));
    }

    @Test
    public void readingsNotNull() {
        Readings readings = basicParser.getReadings(92);
        assertNotNull(readings);
        assertNotNullList(readings.getObservations());
    }

    @Test
    public void readingsHash() {
        assertSortedListHash(-1753926665,
                basicParser.getReadings(92).getObservations(),
                Comparator.comparing(Readings.Observation::getTime));
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
        IndexPage index = basicParser.getIndex(52);
        assertNotNull(index);
        assertNotNullList(index.getIndexes());
    }

    @Test
    public void indexHash() {
        assertSortedListHash(-1312665777,
                basicParser.getIndex(52).getIndexes(),
                Comparator.comparing(IndexPage.IndexData::getCalculationDate));
    }

    @Test
    public void timestampCheck() {
        LocalDateTime start = LocalDateTime.now();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalDateTime created = basicParser.getFindAll().getUpdateTime();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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