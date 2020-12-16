package pl.mini.pw.zanieczyszczenie.data.dataclasses;

import pl.mini.pw.zanieczyszczenie.data.commons.Station;

import java.util.List;

public class FindAll extends DataClass {
    /*
        Stacje pomiarowe:
        pjp-api/rest/station/findAll
     */
    private List<Station> container;
}
