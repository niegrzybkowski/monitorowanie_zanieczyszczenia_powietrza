package pl.mini.pw.zanieczyszczenie.data.testData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Read {
    // TODO: ogarnąć cokolwiek tu się dzieje, patrz test/BasicParserTestFindAll
    String root = System.getProperty("user.dir");
    String seperator = File.separator;

    public String[] getAvailableDates() {
        File f = new File(root + seperator + "testData");
        return f.list();
    }
    public void showAllAvailableData() {
        Path irrelevant = Paths.get(root + seperator + "testData" + seperator);
        try (Stream<Path> walk = Files.walk(Paths.get(root + seperator + "testData"))) {
            // We want to find only regular files
            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.subpath(irrelevant.getNameCount(), x.getNameCount()))
                    .map(x -> x.toString()).collect(Collectors.toList());

            result.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showAllAvailableDataAtSpecificTime(String Date) {
        Path irrelevant = Paths.get(root + seperator + "testData" + seperator + Date);
        try (Stream<Path> walk = Files.walk(Paths.get(root + seperator + "testData" + seperator + Date))) {
            // We want to find only regular files
            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.subpath(irrelevant.getNameCount(), x.getNameCount()))
                    .map(x -> x.toString()).collect(Collectors.toList());

            result.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFindAll(String Date) {

        StringBuilder output = new StringBuilder();

        try {
            FileReader reader = new FileReader(root + seperator + "testData"
                    + seperator + Date + seperator + "findAll.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                output.append(nextLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public String readIndex(String Date, int stationId) {
        StringBuilder output = new StringBuilder();

        try {
            FileReader reader = new FileReader(root + seperator + "testData"
                    + seperator + Date + seperator + "index" + seperator +  "station" + stationId + ".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                output.append(nextLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public String readReadings(String Date, int sensorId) {
        StringBuilder output = new StringBuilder();

        try {
            FileReader reader = new FileReader(root + seperator + "testData"
                    + seperator + Date + seperator + "readings" + seperator +  "sensor" + sensorId + ".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                output.append(nextLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public String readStationSensors(String Date, int stationId) {
        StringBuilder output = new StringBuilder();

        try {
            FileReader reader = new FileReader(root + seperator + "testData"
                    + seperator + Date + seperator + "stationSensors" + seperator +  "station" + stationId + ".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                output.append(nextLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public static void main(String[] args) {
        Read reader = new Read();
        String date = reader.getAvailableDates()[0];
        String data = reader.readFindAll(date);
        System.out.println(data);

        String data2 = reader.readIndex(date, 52);
        System.out.println(data2);

        String data3 = reader.readReadings(date, 92);
        System.out.println(data3);

        String data4 = reader.readStationSensors(date, 52);
        System.out.println(data4);

        reader.showAllAvailableData();
        System.out.println();
        reader.showAllAvailableDataAtSpecificTime(date);
    }
}
