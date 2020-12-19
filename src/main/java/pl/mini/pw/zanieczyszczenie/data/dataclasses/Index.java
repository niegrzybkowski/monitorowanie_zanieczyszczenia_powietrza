package pl.mini.pw.zanieczyszczenie.data.dataclasses;

import pl.mini.pw.zanieczyszczenie.data.commons.IndexData;
import pl.mini.pw.zanieczyszczenie.data.commons.Station;

import java.util.List;

public class Index extends DataClass {
    /*
        Indeks jakości powietrza:
        pjp-api/rest/aqindex/getIndex/{stationId}
     */
    private List<IndexData> indexes;
}
