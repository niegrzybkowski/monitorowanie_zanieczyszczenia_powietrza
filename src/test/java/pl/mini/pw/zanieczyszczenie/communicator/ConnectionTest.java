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
        findAllResponse = new Connection("http://api.gios.gov.pl/pjp-api/rest/station/findAll").getData();
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

    @Test(expected = RuntimeException.class)
    public void throwException1() {
        new Connection("http://api.gios.gov.pl/pjp-api/rest/station/findAll2").getData();
    }
    @Test(expected = RuntimeException.class)
    public void throwException2() {
        new Connection("http://api.gios.gov.pl/pjp-api/rest/station/sensors/0").getData();
    }

    @Test
    public void correctExceptionMessage1() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> new Connection("http://api.gios.gov.pl/pjp-api/rest/station/findAll2").getData());

        String expectedMessage = "HttpResponseCode: 404 null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void correctExceptionMessage2() {
        Exception exception = assertThrows(RuntimeException.class,
                        () -> new Connection("http://api.gios.gov.pl/pjp-api/rest/station/sensors/0").getData());

                String expectedMessage = "HttpResponseCode: 500 null";
                String actualMessage = exception.getMessage();

                assertTrue(actualMessage.contains(expectedMessage));
    }
}