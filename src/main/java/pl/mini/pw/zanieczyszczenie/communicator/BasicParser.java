package pl.mini.pw.zanieczyszczenie.communicator;


import org.json.JSONArray;
import org.json.JSONObject;
import pl.mini.pw.zanieczyszczenie.data.commons.*;
import pl.mini.pw.zanieczyszczenie.communicator.pages.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class BasicParser implements Parser{
    //TODO: podzielić klasę, patrz docs/ParserBreakup.pdf
    private Function<String, String> dataSource;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String giosDataSource(String path) {
        String url = "http://api.gios.gov.pl/pjp-api/rest/";
        return Connection.getDataFromURL(url + path);
    }

    public BasicParser(Function<String, String> dataSource) {
        this.dataSource = dataSource;
    }

    /*
    FindAll
     */
    private FindAll.Station parseStationJSON(JSONObject current) {
        JSONObject current_city = new JSONObject(current.get("city").toString());
        JSONObject current_commune = new JSONObject(current_city.get("commune").toString());

        Object addressStreet = current.get("addressStreet");
        if (addressStreet== JSONObject.NULL) {
            addressStreet = null;
        }
        return new FindAll.Station(
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
    public FindAll getFindAll() {
        String data = dataSource.apply("station/findAll");
        List<FindAll.Station> stations = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(data);
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject current = jsonArray.getJSONObject(i);
            stations.add(parseStationJSON(current));
        }
        return new FindAll(stations);
    }

    /*
    getData
     */
    public Readings parseReadings(String data) {
        List<Readings.Observation> observations = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);

        JSONArray values = new JSONArray(jsonObject.get("values").toString());

        for (int i=0; i<values.length(); i++) {
            JSONObject currentvalue = values.getJSONObject(i);
            Object value =  currentvalue.get("value");
            String dateString = currentvalue.get("date").toString();
            LocalDateTime date = LocalDateTime.parse(dateString, dateTimeFormatter);
            if (value == JSONObject.NULL) {
                value = BigDecimal.ZERO;
            }
            observations.add(new Readings.Observation(date, ((BigDecimal) value).doubleValue()));
        }
        String key = jsonObject.getString("key");
        key = key.replace(".", ""); // PM2.5 zapisujemy jako PM25 (enum)
        return new Readings(PollutionType.valueOf(key),observations);
    }
    @Override
    public Readings getReadings(int sensorID){
        return parseReadings(dataSource.apply("data/getData/" + sensorID));
    }

    /*
    sensors
     */
    public StationSensors parseStationSensors(String data) {
        List<StationSensors.Sensor> sensors = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);

        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject current = jsonArray.getJSONObject(i);
            JSONObject current_param = new JSONObject(current.get("param").toString());

            String key = current_param.getString("paramCode");
            key = key.replace(".", ""); // PM2.5 zapisujemy jako PM25 (enum)
            StationSensors.Sensor sensor = new StationSensors.Sensor(
                    current.getInt("stationId"),
                    current.getInt("id"),
                    PollutionType.valueOf(key));
            sensors.add(sensor);
        }
        return new StationSensors(sensors);
    }
    @Override
    public StationSensors getStationSensors(int stationID) {
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
    public Index parseGetIndex(String data) {
        List<Index.IndexData> indexes = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);

        String[] firstPart = {"st", "so2", "no2", "co", "pm10", "pm25", "o3", "c6h6"};
        String[] secondPart = {"SourceDataDate", "CalcDate", "IndexLevel"};
        PollutionType[] firstPartAsEnum = {PollutionType.STANDARD, PollutionType.SO2, PollutionType.NO2,
                PollutionType.CO, PollutionType.PM10, PollutionType.PM25, PollutionType.O3, PollutionType.C6H6};

        for (String key : firstPart) {
            LocalDateTime sourceDataDate = parseDateTime(jsonObject.get(key + "SourceDataDate").toString());
            LocalDateTime calcDate = parseDateTime(jsonObject.get(key + "CalcDate").toString());

            Object indexLevel;
            if (jsonObject.get(key + "IndexLevel") == JSONObject.NULL) { // w przypadku braku indeksu
                indexLevel = -1;
            } else {
                JSONObject indexLevelInfo = jsonObject.getJSONObject(key + "IndexLevel");
                indexLevel = indexLevelInfo.get("id");
            }

            indexes.add(new Index.IndexData(sourceDataDate, calcDate,
                    ((int) indexLevel), key));
        }

        return new Index(indexes);
    }
    @Override
    public Index getIndex(int stationID) {
        return parseGetIndex(dataSource.apply("aqindex/getIndex/" + stationID));
    }
}
