package pl.mini.pw.zanieczyszczenie.communicator;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionTest {
    static String findAllResponse;
    static String stationSensorsResponse;
    static String readingsResponse;
    static String indexResponse;

    @BeforeClass
    public static void setUp() {
        findAllResponse = new Connection("http://api.gios.gov.pl/pjp-api/rest/findAll").getData();
        stationSensorsResponse = new Connection("http://api.gios.gov.pl/pjp-api/rest/station/sensors/156").getData();
        readingsResponse = new Connection("http://api.gios.gov.pl/pjp-api/rest/data/getData/92").getData();
        indexResponse = new Connection("http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/52").getData();
    }

    @Test
    public void findAllNotNull() {
        assertNotNull(findAllResponse);
    }

    @Test
    public void findAllContainsWarsaw() {
        assertTrue(findAllResponse.contains("\"id\":550,\"stationName\":\"Warszawa-Ursynów\""));
    }

    @Test
    public void sensorsNotNull() {
        assertNotNull(stationSensorsResponse);
    }

    @Test
    public void sensorsBasicContains() {
        assertTrue(stationSensorsResponse.contains("\"stationId\":156"));
    }
}