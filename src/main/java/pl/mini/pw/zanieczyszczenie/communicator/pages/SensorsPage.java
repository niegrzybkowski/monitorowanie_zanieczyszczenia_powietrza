package pl.mini.pw.zanieczyszczenie.communicator.pages;

import java.util.List;
import java.util.Objects;

public class SensorsPage extends APIPage {
    /*
        Stanowiska pomiarowe:
        pjp-api/rest/station/sensors/{stationId}
     */

    private List<Sensor> stationSensors;

    public SensorsPage(List<Sensor> stationSensors) {
        this.stationSensors = stationSensors;
    }

    public List<Sensor> getStationSensors() {
        return stationSensors;
    }

    public static class Sensor {
        private int stationID;
        private int sensorID;
        private String key;

        public Sensor(int stationID, int sensorID, String key) {
            this.stationID = stationID;
            this.sensorID = sensorID;
            this.key = key;
        }

        public int getStationID() {
            return stationID;
        }

        public int getSensorID() {
            return sensorID;
        }

        public String getKey() {
            return key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Sensor sensor = (Sensor) o;

            if (stationID != sensor.stationID) return false;
            if (sensorID != sensor.sensorID) return false;
            return key.equals(sensor.key);
        }

        @Override
        public int hashCode() {
            int result = stationID;
            result = 31 * result + sensorID;
            result = 31 * result + (key != null ? key.hashCode() : 0);
            return result;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SensorsPage sensors = (SensorsPage) o;

        return Objects.equals(stationSensors, sensors.stationSensors);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (stationSensors != null ? stationSensors.hashCode() : 0);
        return result;
    }
}
