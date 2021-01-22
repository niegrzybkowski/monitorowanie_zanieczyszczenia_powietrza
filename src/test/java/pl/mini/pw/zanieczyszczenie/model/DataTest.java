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
    public void checkCachingIndexes() {
        data.getIndexPage(52);

        Assert.assertEquals(1, data.count);

        data.getIndexPage(52);

        Assert.assertEquals(1, data.count);

        data.getIndexPage(14);

        Assert.assertEquals(2, data.count);

        data.getAllIndexPages();

        Assert.assertEquals(9, data.count);

        for (int i = 0; i < 10; i++) {
            data.getIndexPage(11);
            data.getIndexPage(14);
            data.getIndexPage(129);
            data.getAllIndexPages();
        }

        Assert.assertEquals(9, data.count);

        Assert.assertEquals(9, data.getAllIndexPages().size()+1); // +1, bo wczytuje wszystkie ID z findAll
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
    public void checkCachingReadings() {
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

}