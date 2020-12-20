package pl.mini.pw.zanieczyszczenie.communicator.pages;

import pl.mini.pw.zanieczyszczenie.data.commons.PollutionType;

import java.time.LocalDateTime;
import java.util.List;

public class Readings extends APIPage {
    /*
        Dane pomiarowe:
        pjp-api/rest/data/getData/{sensorId}
     */
    private PollutionType key;
    private List<Observation> observations;

    public Readings(PollutionType key, List<Observation> observations) {
        this.key = key;
        this.observations = observations;
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

        @Override
        public String toString() {
            return "Observation{" +
                    "time=" + time +
                    ", value=" + value +
                    '}';
        }
    }
}
