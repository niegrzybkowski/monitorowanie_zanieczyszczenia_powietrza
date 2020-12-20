package pl.mini.pw.zanieczyszczenie.communicator;

import pl.mini.pw.zanieczyszczenie.data.dataclasses.*;

public interface ParserInterface {
    FindAll getFindAll();
    StationSensors getStationSensors(int stationID);
    Readings getReadings(int sensorID);
    Index getIndex(int stationID);
    Snapshot getSnapshot();
}
