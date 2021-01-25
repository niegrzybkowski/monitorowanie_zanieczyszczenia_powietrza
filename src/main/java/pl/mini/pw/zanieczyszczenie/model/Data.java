package pl.mini.pw.zanieczyszczenie.model;

import pl.mini.pw.zanieczyszczenie.communicator.BasicParser;
import pl.mini.pw.zanieczyszczenie.communicator.pages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data implements Model{

    FindAllPage findAllPage;
    Map<Integer, StationInfoPage> stationInfoPages;
    Map<Integer, ReadingsPage> readingsPages;
    Map<Integer, SensorsPage> sensorsPages;

    BasicParser parser;
    int count = 0; // to test correctness of caching

    public Data(BasicParser basicParser) {
        this.findAllPage = null;
        this.readingsPages = new HashMap<>();
        this.sensorsPages = new HashMap<>();
        this.stationInfoPages = new HashMap<>();
        this.parser = basicParser;
    }
    public Data() {
        this.findAllPage = null;
        this.readingsPages = new HashMap<>();
        this.sensorsPages = new HashMap<>();
        this.stationInfoPages = new HashMap<>();
        this.parser = new BasicParser(BasicParser::giosDataSource);
    }




    public FindAllPage getFindAll() {
        if (findAllPage == null) {
            findAllPage = parser.getFindAll();
            count++;
        }
        return findAllPage;
    }

    @Override
    public List<StationInfoPage> getStationInfoPages() {
        findAllPage = getFindAll();

        for (FindAllPage.Station station: findAllPage.getContainer()) {
            int stationId = station.getId();
            if (!stationInfoPages.containsKey(stationId)) {
                IndexPage indexPage = parser.getIndex(stationId);
                stationInfoPages.put(stationId,
                        new StationInfoPage(indexPage.getIndexes(),
                                stationId,
                                station.getGeographicLat(),
                                station.getGeographicLon()));
                count++;
            }
        }


        return new ArrayList<>(stationInfoPages.values());
    }

    @Override
    public StationInfoPage getStationInfoPage(int stationId) {
        findAllPage = getFindAll();
        FindAllPage.Station station = findAllPage.getContainer().stream()
                .filter(e -> e.getId()==stationId)
                .findFirst()
                .orElse(null);
        if (station == null) {
            return null;
        }
        if (!stationInfoPages.containsKey(stationId)) {
            IndexPage indexPage = parser.getIndex(stationId);
            stationInfoPages.put(stationId,
                    new StationInfoPage(indexPage.getIndexes(),
                            stationId,
                            station.getGeographicLat(),
                            station.getGeographicLon()));
            count++;
        }
        return stationInfoPages.get(stationId);
    }

    @Override
    public ReadingsPage getReadingsPage(int stationId, String key) {
        int sensorId = getSensorsPage(stationId).getStationSensors()
                .stream()
                .filter(x -> x.getKey().equals(key))
                .map(SensorsPage.Sensor::getSensorID)
                .findFirst()
                .orElse(-1);
        if (sensorId==-1) {
            return null;
        }
        return getReadingsPage(sensorId);
    }


    ReadingsPage getReadingsPage(int sensorId) {
        if (!readingsPages.containsKey(sensorId)) {
            readingsPages.put(sensorId, parser.getReadings(sensorId));
            count++;
        }
        return readingsPages.get(sensorId);
    }

    @Override
    public SensorsPage getSensorsPage(int stationId) {
        if (!sensorsPages.containsKey(stationId)) {
            sensorsPages.put(stationId, parser.getStationSensors(stationId));
            count++;
        }
        return sensorsPages.get(stationId);
    }

    private void refreshStationInfoPages() {
        if (stationInfoPages==null) {
            return;
        }

        for (StationInfoPage station: stationInfoPages.values()) {
            if (station.shouldRefresh()) {
                stationInfoPages.remove(station.getId());
                getStationInfoPage(station.getId());
            }
        }
    }
    private void refreshReadingsPages() {
        if (readingsPages==null) {
            return;
        }

        for (int sensorID: readingsPages.keySet()) {
            ReadingsPage reading = readingsPages.get(sensorID);
            if (reading.shouldRefresh()) {
                readingsPages.remove(sensorID);
                getReadingsPage(sensorID);
            }
        }
    }
    private void refreshSensorsPages() {
        if (sensorsPages==null) {
            return;
        }

        for (int stationID: sensorsPages.keySet()) {
            SensorsPage sensor = sensorsPages.get(stationID);
            if (sensor.shouldRefresh()) {
                sensorsPages.remove(stationID);
                getSensorsPage(stationID);
            }
        }
    }

    @Override
    public void refresh() {
        refreshStationInfoPages();
        refreshReadingsPages();
        refreshSensorsPages();
    }

    public static void main(String[] args) {
        Model model = new Data(
                new BasicParser(BasicParser::loadFromTestResources)
        );

        System.out.println(model.getReadingsPage(14, "PM10"));
    }
}
