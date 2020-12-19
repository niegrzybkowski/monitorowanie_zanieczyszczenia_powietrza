package pl.mini.pw.zanieczyszczenie.data.dataclasses;

import pl.mini.pw.zanieczyszczenie.data.commons.PollutionType;

import java.time.LocalDateTime;
import java.util.List;

public class Readings extends DataClass {
    /*
        Dane pomiarowe:
        pjp-api/rest/data/getData/{sensorId}
     */
    private PollutionType key; //TODO: zmienić na enum
    private List<Observation> observations;

    public PollutionType getKey() {
        return key;
    }

    public void setKey(PollutionType key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Readings{" +
                "key=" + key +
                ", observations=" + observations +
                '}';
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    public static class Observation {
        private LocalDateTime time;
        private double value;
        public Observation(LocalDateTime time, double value) {
            this.time = time;
            this.value = value;
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
