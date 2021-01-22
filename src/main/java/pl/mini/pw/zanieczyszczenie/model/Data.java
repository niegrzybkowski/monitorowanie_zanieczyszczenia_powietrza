package pl.mini.pw.zanieczyszczenie.model;

import pl.mini.pw.zanieczyszczenie.communicator.BasicParser;
import pl.mini.pw.zanieczyszczenie.communicator.Parser;
import pl.mini.pw.zanieczyszczenie.communicator.pages.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Data implements Model{

    FindAllPage findAllPage;
//    Map<Integer, IndexPage> indexPages;
    Map<Integer, StationInfoPage> stationInfoPages;
    Map<Integer, ReadingsPage> readingsPages;
    Map<Integer, SensorsPage> sensorsPages;

    BasicParser parser;
    int count = 0; // to test correctness of caching

    public Data(BasicParser basicParser) {
        this.findAllPage = null;
//        this.indexPages = new HashMap<>();
        this.readingsPages = new HashMap<>();
        this.sensorsPages = new HashMap<>();
        this.stationInfoPages = new HashMap<>();
        this.parser = basicParser;
    }
    public Data() {
        this.findAllPage = null;
//        this.indexPages = new HashMap<>();
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

//    @Override
//    public IndexPage getIndexPage(int stationId) {
//        if (!indexPages.containsKey(stationId)) {
//            indexPages.put(stationId, parser.getIndex(stationId));
//            count++;
//        }
//        return indexPages.get(stationId);
//    }

//    @Override
//    public List<IndexPage> getAllIndexPages() {
//        if (findAllPage == null) {
//            getFindAll();
//        }
//
//        for (FindAllPage.Station station: findAllPage.getContainer()) {
//            int currentStationId = station.getId();
//            if (!indexPages.containsKey(currentStationId)) {
//                indexPages.put(currentStationId, parser.getIndex(currentStationId));
//                count++;
//            }
//        }
//        return new ArrayList<>(indexPages.values());
//    }

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
    public ReadingsPage getReadingsPage(int sensorId) {
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

    public static void main(String[] args) {
        Data data = new Data();
    }
}
