package pl.mini.pw.zanieczyszczenie.data.dataclasses;

import pl.mini.pw.zanieczyszczenie.data.commons.PollutionType;

public class StationSensors extends DataClass {
    /*
        Stanowiska pomiarowe:
        pjp-api/rest/station/sensors/{stationId}
     */
    private int stationID;
    private int sensorID;
    private PollutionType key;

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public PollutionType getKey() {
        return key;
    }

    public void setKey(PollutionType key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "StationSensors{" +
                "stationID=" + stationID +
                ", sensorID=" + sensorID +
                ", key=" + key +
                '}';
    }
}
