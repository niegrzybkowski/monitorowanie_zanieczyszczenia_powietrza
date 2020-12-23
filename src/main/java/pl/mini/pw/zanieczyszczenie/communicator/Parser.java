package pl.mini.pw.zanieczyszczenie.communicator;

import pl.mini.pw.zanieczyszczenie.communicator.pages.*;

public interface Parser {
    FindAll getFindAll();
    StationSensors getStationSensors(int stationID);
    Readings getReadings(int sensorID);
    IndexPage getIndex(int stationID);
}
