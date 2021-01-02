package pl.mini.pw.zanieczyszczenie.communicator;

import pl.mini.pw.zanieczyszczenie.communicator.pages.*;

import java.io.IOException;

public interface Parser {
    FindAllPage getFindAll() throws IOException;
    SensorsPage getStationSensors(int stationID) throws IOException;
    ReadingsPage getReadings(int sensorID) throws IOException;
    IndexPage getIndex(int stationID) throws IOException;
}
