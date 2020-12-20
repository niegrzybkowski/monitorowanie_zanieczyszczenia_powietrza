package pl.mini.pw.zanieczyszczenie.communicator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionTest {
    static String data;
    @BeforeClass
    public static void setUp() {
        String URLstring = "http://api.gios.gov.pl/pjp-api/rest/station/sensors/156";
        Connection connection = new Connection(URLstring);
        data = connection.getData();
    }

    @Test
    public void testConnection() {
        assertNotNull(data);
    }

    @Test
    public void testContents() {
        assertTrue(data.contains("\"stationId\":156"));
    }
}