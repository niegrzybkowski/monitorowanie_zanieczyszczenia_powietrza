package pl.mini.pw.zanieczyszczenie.data.testData;

import pl.mini.pw.zanieczyszczenie.communicator.BasicParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Write {
    BasicParser basicParser = new BasicParser();

    String root = System.getProperty("user.dir");
    String seperator = File.separator;
    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH-00"));
    String destination = seperator + "testData" + seperator + time + seperator;

    int exampleStationId = 52; //14
    int exampleSensorId = 92;

    public void setExampleStationId(int exampleStationId) {
        this.exampleStationId = exampleStationId;
    }
    public void setExampleSensorId(int exampleSensorId) {
        this.exampleSensorId = exampleSensorId;
    }


    public void saveFindAll() {
        String filename = "findAll.txt";

        File file = new File(root+destination, filename);
        file.getParentFile().mkdirs();

        String data = basicParser.readFindAll();

        try {
            FileOutputStream outputStream = new FileOutputStream(root + destination + filename);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveIndex(int stationId) {
        String filename = "station" + stationId + ".txt";
        String thisDestination = destination + "index" + seperator;

        File file = new File(root+thisDestination, filename);
        file.getParentFile().mkdirs();

        String data = basicParser.readGetIndex(stationId);

        try {
            FileOutputStream outputStream = new FileOutputStream(root + thisDestination + filename);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveReadings(int sensorId) {
        String filename = "sensor" + sensorId + ".txt";
        String thisDestination = destination + "readings" + seperator;

        File file = new File(root + thisDestination, filename);
        file.getParentFile().mkdirs();

        String data = basicParser.readReadings(sensorId);

        try {
            FileOutputStream outputStream = new FileOutputStream(root + thisDestination + filename);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveStationSensors(int stationId) {
        String filename = "station" + stationId + ".txt";
        String thisDestination = destination + "stationSensors" + seperator;

        File file = new File(root + thisDestination, filename);
        file.getParentFile().mkdirs();

        String data = basicParser.readStationSensors(stationId);

        try {
            FileOutputStream outputStream = new FileOutputStream(root + thisDestination + filename);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll(boolean withFindAll) {
        if (withFindAll) {
            saveFindAll();
        }
        saveIndex(exampleStationId);
        saveStationSensors(exampleStationId);
        saveReadings(exampleSensorId);
    }

    public void saveAllAt(boolean withFindAll, int stationId, int sensorId) {
        if (withFindAll) {
            saveFindAll();
        }
        saveIndex(stationId);
        saveStationSensors(stationId);
        saveReadings(sensorId);
    }


    public static void main(String[] args) {
        Write writer = new Write();
        writer.saveAll(false);
    }
}
