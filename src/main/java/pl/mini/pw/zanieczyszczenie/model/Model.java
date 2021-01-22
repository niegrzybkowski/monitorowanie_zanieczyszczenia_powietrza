package pl.mini.pw.zanieczyszczenie.model;

import pl.mini.pw.zanieczyszczenie.communicator.pages.*;

import java.util.List;

public interface Model {
    FindAllPage getFindAll();
    IndexPage getIndexPage(int stationId);
    List<IndexPage> getAllIndexPages();
    ReadingsPage getReadingsPage(int sensorId);
    SensorsPage getSensorsPage(int stationId);
}
