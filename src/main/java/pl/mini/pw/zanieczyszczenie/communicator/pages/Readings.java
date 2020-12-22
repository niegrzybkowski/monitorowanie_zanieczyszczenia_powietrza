package pl.mini.pw.zanieczyszczenie.communicator.pages;

import java.time.LocalDateTime;
import java.util.List;

public class Readings extends APIPage {
    /*
        Dane pomiarowe:
        pjp-api/rest/data/getData/{sensorId}
     */
    private String key;
    private List<Observation> observations;

    public Readings(String key, List<Observation> observations) {
        this.key = key;
        this.observations = observations;
    }

    public String getKey() {
        return key;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    @Override
    public String toString() {
        return "Readings{" +
                "key=" + key +
                ", observations=" + observations +
                '}';
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
    }
}
