package pl.mini.pw.zanieczyszczenie.model;

import org.junit.Assert;
import org.junit.Test;
import pl.mini.pw.zanieczyszczenie.communicator.BasicParser;
import pl.mini.pw.zanieczyszczenie.communicator.TestUtilities;


public class DataTest {

    static BasicParser basicParser = new BasicParser(TestUtilities::loadFromTestResources);

    Data data = new Data(basicParser);

    @Test
    public void checkCachingSensors() {
        data.getSensorsPage(52);

        Assert.assertEquals(1, data.count);

        data.getSensorsPage(52);

        Assert.assertEquals(1, data.count);

        data.getSensorsPage(14);

        Assert.assertEquals(2, data.count);

        Assert.assertEquals(2, data.sensorsPages.size());

    }

    @Test
    public void checkCachingFindAll() {
        data.getFindAll();

        Assert.assertEquals(1, data.count);

        for (int i = 0; i < 10; i++) {
            data.getFindAll();
        }

        Assert.assertEquals(1, data.count);

    }

    @Test
    public void checkCachingReadingsBySensorId() {
        data.getReadingsPage(50);

        Assert.assertEquals(1, data.count);
        Assert.assertEquals(1, data.readingsPages.size());

        for (int i = 0; i < 15; i++) {
            data.getReadingsPage(50);
        }

        Assert.assertEquals(1, data.count);
        Assert.assertEquals(1, data.readingsPages.size());

        data.getReadingsPage(660);

        Assert.assertEquals(2, data.count);
        Assert.assertEquals(2, data.readingsPages.size());
    }

    @Test
    public void checkCachingReadings() {
        data.getReadingsPage(14, "PM10");

        Assert.assertEquals(2, data.count);
        // +1 bo musial zgarnac dostepne sensorId w danej stacji
        Assert.assertEquals(2, data.readingsPages.size()+1);

        data.getReadingsPage(11, "NO2");

        Assert.assertEquals(4, data.count);
        Assert.assertEquals(4, data.readingsPages.size()+2);

        data.getReadingsPage(11, "O3");

        Assert.assertEquals(5, data.count);
        Assert.assertEquals(5, data.readingsPages.size()+2);

        for (int i = 0; i < 15; i++) {
            data.getReadingsPage(11, "O3");
            data.getReadingsPage(11, "NO2");
            data.getReadingsPage(14, "PM10");
        }

        Assert.assertEquals(5, data.count);
        Assert.assertEquals(5, data.readingsPages.size()+2);


        data.getReadingsPage(11, "PM10"); // nie istnieje

        Assert.assertEquals(5, data.count);
        Assert.assertEquals(5, data.readingsPages.size()+2);

    }

    @Test
    public void checkCachingStationInfo() {
        data.getStationInfoPage(52);

        Assert.assertEquals(2, data.count);
        Assert.assertEquals(2, data.stationInfoPages.size()+1);

        data.getStationInfoPages();

        Assert.assertEquals(9, data.count);
        Assert.assertEquals(9, data.stationInfoPages.size()+1);



        for (int i = 0; i < 15; i++) {
            data.getStationInfoPages();
            data.getStationInfoPage(11);
            data.getStationInfoPage(109);
            data.getStationInfoPage(117);
        }

        Assert.assertEquals(9, data.count);
        Assert.assertEquals(9, data.stationInfoPages.size()+1);

    }

}