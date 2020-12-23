package pl.mini.pw.zanieczyszczenie.communicator;

import pl.mini.pw.zanieczyszczenie.communicator.pages.*;

public interface Parser {
    FindAllPage getFindAll();
    SensorsPage getStationSensors(int stationID);
    ReadingsPage getReadings(int sensorID);
    IndexPage getIndex(int stationID);
}
