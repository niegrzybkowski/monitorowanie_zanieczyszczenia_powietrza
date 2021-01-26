package pl.mini.pw.zanieczyszczenie.model;

import pl.mini.pw.zanieczyszczenie.communicator.pages.*;

import java.util.List;

public interface Model {
    List<StationInfoPage> getStationInfoPages();
    StationInfoPage getStationInfoPage(int stationId);
    ReadingsPage getReadingsPage(int stationId, String key);
    SensorsPage getSensorsPage(int stationId);
    void refresh() throws InterruptedException;
}
