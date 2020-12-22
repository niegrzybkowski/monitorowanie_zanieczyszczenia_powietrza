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
        findAllResponse = Connection.getDataFromURL("http://api.gios.gov.pl/pjp-api/rest/station/findAll");
        stationSensorsResponse = Connection.getDataFromURL("http://api.gios.gov.pl/pjp-api/rest/station/sensors/156");
        readingsResponse = Connection.getDataFromURL("http://api.gios.gov.pl/pjp-api/rest/data/getData/92");
        indexResponse = Connection.getDataFromURL("http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/52");
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

    @Test
    public void readingsNotNull() {
        assertNotNull(readingsResponse);
    }

    @Test
    public void readingsStartWithKey() {
        assertTrue(readingsResponse.startsWith("{\"key\":\"PM10\",\"values\":["));
    }

    @Test
    public void indexNotNull() {
        assertNotNull(indexResponse);
    }

    @Test
    public void indexStartsWithID() {
        assertTrue(indexResponse.startsWith("{\"id\":52,"));
    }

    @Test
    public void throwException1() {
        try {
            Connection.getDataFromURL("http://api.gios.gov.pl/pjp-api/rest/station/findAll2");
        } catch (Exception e) {
            assert(true);
        }
    }
    @Test
    public void throwException2() {
        try {
            Connection.getDataFromURL("http://api.gios.gov.pl/pjp-api/rest/station/sensors/0");
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void correctExceptionMessage1() {
        try {
            Connection.getDataFromURL("http://api.gios.gov.pl/pjp-api/rest/station/findAll2");
        } catch (Exception e) {
            String expectedMessage =
                    "URL http://api.gios.gov.pl/pjp-api/rest/station/findAll2 produced HttpResponseCode: 404 null";
            String actualMessage = e.getMessage();
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    @Test
    public void correctExceptionMessage2() {
        try {
            Connection.getDataFromURL("http://api.gios.gov.pl/pjp-api/rest/station/sensors/0");
        } catch (Exception e) {
            String expectedMessage =
                    "URL http://api.gios.gov.pl/pjp-api/rest/station/sensors/0 produced HttpResponseCode: 500 null";
            String actualMessage = e.getMessage();
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }
}