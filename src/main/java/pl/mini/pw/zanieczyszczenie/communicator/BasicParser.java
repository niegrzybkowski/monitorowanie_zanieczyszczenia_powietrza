package pl.mini.pw.zanieczyszczenie.communicator;


import org.json.JSONArray;
import org.json.JSONObject;
import pl.mini.pw.zanieczyszczenie.communicator.pages.*;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class BasicParser implements Parser{
    private final Function<String, String> dataSource;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String giosDataSource(String path) {
        String url = "http://api.gios.gov.pl/pjp-api/rest/";
        return Connection.getDataQuiet(url + path);
    }

    public static String loadFromTestResources(String path) {
        try {
            var dataStream  = new BufferedReader(
                    new FileReader("dummyAPI/" + path + ".json")
            );
            if (dataStream == null) {
                System.err.println("Error loading from resource: dataStream is null");
                return "";
            }
            var str = dataStream.lines().collect(Collectors.joining());
            return str;
        } catch (IOException e) {
            System.err.println("Error loading from resource: " + e.getMessage());
            return "";
        }
    }

    public BasicParser(Function<String, String> dataSource) {
        this.dataSource = dataSource;
    }

    /*
    FindAll
     */
    private FindAllPage.Station parseStationJSON(JSONObject current) {
        JSONObject current_city = new JSONObject(current.get("city").toString());
        JSONObject current_commune = new JSONObject(current_city.get("commune").toString());

        Object addressStreet = current.get("addressStreet");
        if (addressStreet== JSONObject.NULL) {
            addressStreet = null;
        }
        return new FindAllPage.Station(
                current.getInt("id"),
                current.getString("stationName"),
                current.getDouble("gegrLat"),
                current.getDouble("gegrLon"),
                current_city.getInt("id"),
                current_city.get("name").toString(),
                current_commune.get("communeName").toString(),
                current_commune.get("districtName").toString(),
                current_commune.get("provinceName").toString(),
                (String) addressStreet
        );
    }
    @Override
    public FindAllPage getFindAll() {
        String data = dataSource.apply("station/findAll");
        List<FindAllPage.Station> stations = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(data);
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject current = jsonArray.getJSONObject(i);
            stations.add(parseStationJSON(current));
        }
        return new FindAllPage(stations);
    }

    /*
    getData
     */
    public ReadingsPage parseReadings(String data) {
        List<ReadingsPage.Observation> observations = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);

        JSONArray values = new JSONArray(jsonObject.get("values").toString());

        for (int i=0; i<values.length(); i++) {
            JSONObject currentValue = values.getJSONObject(i);
            Object value =  currentValue.get("value");
            String dateString = currentValue.get("date").toString();
            LocalDateTime date = LocalDateTime.parse(dateString, dateTimeFormatter);
            if (value == JSONObject.NULL) {
                value = null; // TODO: co tu się dzieje, czemu tu jest BigDecimal?
            }
            try {
                observations.add(new ReadingsPage.Observation(date, (Double) value));
            } catch(Exception e) {
                try {
                    observations.add(new ReadingsPage.Observation(date, ((Integer) value).doubleValue()));
                } catch (Exception e1) {
                    try {
                        observations.add(new ReadingsPage.Observation(date, ((BigDecimal) value).doubleValue()));
                    } catch(Exception e2) {
                        e.printStackTrace();
                    }
                }
            }
        }
        String key = jsonObject.getString("key");
        key = key.replace(".", ""); // PM2.5 zapisujemy jako PM25 (enum)
        return new ReadingsPage(key,observations);
    }
    @Override
    public ReadingsPage getReadings(int sensorID){
        return parseReadings(dataSource.apply("data/getData/" + sensorID));
    }

    /*
    sensors
     */
    public SensorsPage parseStationSensors(String data) {
        List<SensorsPage.Sensor> sensors = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);

        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject current = jsonArray.getJSONObject(i);
            JSONObject current_param = new JSONObject(current.get("param").toString());

            String key = current_param.getString("paramCode");
            key = key.replace(".", ""); // PM2.5 zapisujemy jako PM25 (enum)
            SensorsPage.Sensor sensor = new SensorsPage.Sensor(
                    current.getInt("stationId"),
                    current.getInt("id"),
                    key);
            sensors.add(sensor);
        }
        return new SensorsPage(sensors);
    }
    @Override
    public SensorsPage getStationSensors(int stationID) {
        return parseStationSensors(dataSource.apply("station/sensors/" + stationID));
    }

    /*
    getIndex
     */
    private LocalDateTime parseDateTime(String dateString) {
        try { // api czasami pokazuje date inaczej
            long epochDate = Long.parseLong(dateString);
            return LocalDateTime.ofEpochSecond(epochDate / 1000, 0,
                    ZoneId.of("Europe/Warsaw").getRules().getOffset(LocalDateTime.now()));
        } catch (NumberFormatException e) {
            if (dateString.equals(JSONObject.NULL.toString())) {
                return null;
            } else {
                return LocalDateTime.parse(dateString, dateTimeFormatter);
            }
        }

    }
    public IndexPage parseGetIndex(String data) {
        List<IndexPage.IndexData> indexes = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);

        String[] prefixes = {"st", "so2", "no2", "co", "pm10", "pm25", "o3", "c6h6"};

        for (String key : prefixes) {
            LocalDateTime sourceDataDate = parseDateTime(jsonObject.get(key + "SourceDataDate").toString());
            LocalDateTime calcDate = parseDateTime(jsonObject.get(key + "CalcDate").toString());

            int indexLevel;
            if (jsonObject.get(key + "IndexLevel") == JSONObject.NULL) { // w przypadku braku indeksu
                indexLevel = -1;
            } else {
                JSONObject indexLevelInfo = jsonObject.getJSONObject(key + "IndexLevel");
                indexLevel = (int) indexLevelInfo.get("id");
            }

            indexes.add(new IndexPage.IndexData(sourceDataDate, calcDate,
                    indexLevel, key));
        }

        return new IndexPage(indexes);
    }
    @Override
    public IndexPage getIndex(int stationID) {
        return parseGetIndex(dataSource.apply("aqindex/getIndex/" + stationID));
    }
}
