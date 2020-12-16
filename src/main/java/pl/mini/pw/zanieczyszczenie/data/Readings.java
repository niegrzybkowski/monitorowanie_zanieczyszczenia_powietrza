package pl.mini.pw.zanieczyszczenie.data;

import java.time.LocalDateTime;
import java.util.List;

public class Readings extends DataClass {
    /*
        Dane pomiarowe:
        pjp-api/rest/data/getData/{sensorId}
     */
    private String key; //TODO: zmienić na enum
    private List<Observation> observations;


    public static class Observation {
        private LocalDateTime time;
        private double value;
    }
}
