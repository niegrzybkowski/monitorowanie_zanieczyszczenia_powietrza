package pl.mini.pw.zanieczyszczenie.communicator.sources;

import java.net.URL;

public class GIOSDataSource implements AirQualityDataSource {
    private static final String api = "http://api.gios.gov.pl/pjp-api/rest/";

    @Override
    public String getData(String pathInAPI) {
        return null;
    }
}
