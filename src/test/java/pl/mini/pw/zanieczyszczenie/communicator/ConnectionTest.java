package pl.mini.pw.zanieczyszczenie.communicator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testConnection() {
        String URLstring = "http://api.gios.gov.pl/pjp-api/rest/station/sensors/156";
        Connection connection = new Connection(URLstring);
        System.out.println(connection.getData());
    }
}