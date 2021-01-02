package pl.mini.pw.zanieczyszczenie.communicator;

import pl.mini.pw.zanieczyszczenie.communicator.pages.FindAllPage;
import pl.mini.pw.zanieczyszczenie.communicator.pages.SensorsPage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public final class TestUtilities {
    private static final ClassLoader classLoader = TestUtilities.class.getClassLoader();

    public static void assertNotNullList(List<?> list) {
        assertNotNull(list);
        assertNotEquals(0, list.size());
        assertNotNull(list.get(0));
    }

    public static <T> void assertSortedListHash(int expectedHashCode, List<T> list, Comparator<T> comparator) {
        list.sort(comparator);
        assertEquals("HashCode mismatch, expected: " + expectedHashCode + " got " + list.hashCode(),
                expectedHashCode, list.hashCode());
    }

    public static String loadFromTestResources(String path) {
        try {
            var dataStream = classLoader.getResourceAsStream("dummyAPI/" + path + ".json");
            if (dataStream == null) {
                System.err.println("Error loading from resource: dataStream is null");
                return "";
            }
            return new String(dataStream.readAllBytes());
        } catch (IOException e) {
            System.err.println("Error loading from resource: " + e.getMessage());
            return "";
        }
    }

    private static final String root = System.getProperty("user.dir");
    private static final String fs = File.separator;

    private static void writerWrapper(String dest, String filename, String data) {
        File dirs = new File(root + fs + "temp" + fs + dest);
        new File(root + fs + "temp" + fs + dest + fs + filename);
        if(dirs.mkdirs()) {
            System.out.println("Directory created: " + root + fs + "temp" + fs + dest);
        }
        try {
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(root + fs + "temp" + fs + dest + fs + filename),
                            StandardCharsets.UTF_8
                    )
            );
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        /*
            Tworzy snapshot API i zapisuje
            Jak nie znajdzie resources/dummyAPI/station/findAll.json to pobiera nowe i ciągnie z giosu pełne

         */
        Parser onlineParser = new BasicParser(BasicParser::giosDataSource);
        Parser trimmed = new BasicParser(TestUtilities::loadFromTestResources);

        FindAllPage findAll = trimmed.getFindAll();
        if(findAll == null || findAll.getContainer() == null || findAll.getContainer().size() == 0) {
            System.err.println("No trimmed source found, downloading full findAll.json");
            System.err.println("Move the downloaded file from temp/station/findAll.json to src/test/resources/dummyAPI/station/findAll.json");
            System.err.println("NOTE: DO NOT RUN THIS WITH THE FULL findAll.json, trim it first to a few stations");
            writerWrapper("station",
                    "findAll.json",
                    BasicParser.giosDataSource("station/findAll"));
            return;
        }
        for(var station: findAll.getContainer()) {
            int stationID = station.getId();

            // sensors
            writerWrapper("station"+ fs + "sensors",
                    stationID + ".json",
                    BasicParser.giosDataSource("station/sensors/" + stationID));

            // index
            writerWrapper("aqindex"+ fs + "getIndex",
                    stationID + ".json",
                    BasicParser.giosDataSource("aqindex/getIndex/" + stationID));

            // readings
            SensorsPage sensors = onlineParser.getStationSensors(stationID);
            for(var sensor: sensors.getStationSensors()) {
                writerWrapper(
                        "data" + fs + "getData",
                        sensor.getSensorID() + ".json",
                        BasicParser.giosDataSource("data/getData/" + sensor.getSensorID())
                );
            }
        }
    }
}
