package pl.mini.pw.zanieczyszczenie.data.dataclasses;

import java.util.Map;

public class Snapshot extends DataClass {
    /*
      Class will contain everything that can be downloaded from the API for testing purposes
     */

    private FindAll findAll;
    private Map<Integer, StationSensors> stationSensors;
    private Map<Integer, Readings> readings;
    private Map<Integer, Index> index;
}
