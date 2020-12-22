package pl.mini.pw.zanieczyszczenie.communicator;

import pl.mini.pw.zanieczyszczenie.communicator.pages.StationSensors;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public final class TestUtilities {
    private static ClassLoader classLoader = TestUtilities.class.getClassLoader();

    public static void notNullListHelper(List<?> list) {
        assertNotNull(list);
        assertNotEquals(0, list.size());
        assertNotNull(list.get(0));
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

    public static void main(String[] args) {
        /*
            Tworzy snapshot API i zapisuje
         */
        Parser onlineParser = new BasicParser(BasicParser::giosDataSource);
        Parser trimmed = new BasicParser(TestUtilities::loadFromTestResources);

        var findAll = trimmed.getFindAll();
        if(findAll == null || findAll.getContainer() == null || findAll.getContainer().size() == 0) {
            System.err.println("No trimmed source, downloading full");
            findAll = onlineParser.getFindAll();
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
            StationSensors sensors = onlineParser.getStationSensors(stationID);
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
