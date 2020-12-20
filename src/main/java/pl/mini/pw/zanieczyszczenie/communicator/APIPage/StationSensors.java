package pl.mini.pw.zanieczyszczenie.communicator.APIPage;

import pl.mini.pw.zanieczyszczenie.data.commons.PollutionType;

import java.util.List;

public class StationSensors extends APIPage {
    /*
        Stanowiska pomiarowe:
        pjp-api/rest/station/sensors/{stationId}
     */

    private List<Sensor> stationSensors;

    public StationSensors(List<Sensor> stationSensors) {
        this.stationSensors = stationSensors;
    }

    public static class Sensor {
        private int stationID;
        private int sensorID;
        private PollutionType key;

        public Sensor(int stationID, int sensorID, PollutionType key) {
            this.stationID = stationID;
            this.sensorID = sensorID;
            this.key = key;
        }

        @Override
        public String toString() {
            return "Sensor{" +
                    "stationID=" + stationID +
                    ", sensorID=" + sensorID +
                    ", key=" + key +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StationSensors{" +
                "stationSensors=" + stationSensors +
                '}';
    }
}
