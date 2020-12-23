package pl.mini.pw.zanieczyszczenie.communicator.pages;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ReadingsPage extends APIPage {
    /*
        Dane pomiarowe:
        pjp-api/rest/data/getData/{sensorId}
     */
    private String key;
    private List<Observation> observations;

    public ReadingsPage(String key, List<Observation> observations) {
        this.key = key;
        this.observations = observations;
    }

    public String getKey() {
        return key;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public static class Observation {
        private LocalDateTime time;
        private double value;
        public Observation(LocalDateTime time, double value) {
            this.time = time;
            this.value = value;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Observation{" +
                    "time=" + time +
                    ", value=" + value +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Observation that = (Observation) o;

            if (Double.compare(that.value, value) != 0) return false;
            return Objects.equals(time, that.time);
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = time != null ? time.hashCode() : 0;
            temp = Double.doubleToLongBits(value);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }

    @Override
    public String toString() {
        return "Readings{" +
                "key=" + key +
                ", observations=" + observations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ReadingsPage readings = (ReadingsPage) o;

        if (!Objects.equals(key, readings.key)) return false;
        return Objects.equals(observations, readings.observations);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (observations != null ? observations.hashCode() : 0);
        return result;
    }
}
