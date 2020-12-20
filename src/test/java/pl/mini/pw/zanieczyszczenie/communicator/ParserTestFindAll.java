package pl.mini.pw.zanieczyszczenie.communicator;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.mini.pw.zanieczyszczenie.communicator.pages.FindAll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ParserTestFindAll {
    static final FindAll.Station dzialoszyn = new FindAll.Station(
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
    static FindAll parserOutput;
    static final int sortedContainerHash = -2037789062;

    @BeforeClass
    public static void beforeClass() throws IOException {
        String data = new String(ParserTestFindAll
                .class
                .getClassLoader()
                .getResourceAsStream("findAll_snap.json")
                .readAllBytes()
        );
        Parser p = new Parser();
        parserOutput = p.parseFindAll(data);
    }

    @Test
    public void notNull() {
        assertNotNull(parserOutput);
    }

    @Test
    public void containsDzialoszyn() {
        assertNotNull("FindAll container is null", parserOutput.getContainer());
        ArrayList<FindAll.Station> list = parserOutput.getContainer()
                .stream()
                .filter((x) -> x.getId()==14)
                .collect(Collectors.toCollection(ArrayList::new));
        assertEquals("Duplicate ID",1, list.size());
        assertEquals("Name mismatch", "Działoszyn", list.get(0).getStationName());
        assertEquals("FindAll.Station.equals() failure", dzialoszyn, list.get(0));
    }
    @Test
    public void testHash() {
        var list = parserOutput.getContainer();
        list.sort(Comparator.comparingInt(FindAll.Station::getId));
        assertEquals("HashCode mismatch", sortedContainerHash, list.hashCode());
    }
}