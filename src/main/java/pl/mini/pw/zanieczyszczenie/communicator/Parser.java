package pl.mini.pw.zanieczyszczenie.communicator;


import org.json.JSONArray;
import org.json.JSONObject;
import pl.mini.pw.zanieczyszczenie.data.commons.*;
import pl.mini.pw.zanieczyszczenie.data.dataclasses.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Parser {
    private String URLstring = "http://api.gios.gov.pl/pjp-api/rest/";

    /*
    FindAll
     */
    public String readFindAll() {
        String findAllURL_tail = "station/findAll";
        URLstring = URLstring + findAllURL_tail;
        Connection connection = new Connection(URLstring);
        return connection.getData();
    }
    public List<Station> parseFindAll(String data) {
        List<Station> stations = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(data);
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject current = jsonArray.getJSONObject(i);
            JSONObject current_city = new JSONObject(current.get("city").toString());
            JSONObject current_commune = new JSONObject(current_city.get("commune").toString());

            Object addressStreet = current.get("addressStreet");
            if (addressStreet== JSONObject.NULL) {
                addressStreet = null;
            }
            stations.add(new Station(
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
            ));
        }
        return stations;
    }


    /*
    getData
     */
    public String readReadings(int sensorId) {
        String getDataURL_tail = "data/getData/" + sensorId;
        URLstring = URLstring + getDataURL_tail;
        Connection connection = new Connection(URLstring);
        return connection.getData();
    }
    public Readings parseReadings(String data) {
        Readings readings = new Readings();
        List<Readings.Observation> observations = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        JSONArray values = new JSONArray(jsonObject.get("values").toString());

        for (int i=0; i<values.length(); i++) {
            JSONObject currentvalue = values.getJSONObject(i);
            Object value =  currentvalue.get("value");
            String dateString = currentvalue.get("date").toString();
            LocalDateTime date = LocalDateTime.parse(dateString, formatter);
            if (value == JSONObject.NULL) {
                value = BigDecimal.ZERO;
            }
            observations.add(new Readings.Observation(date, ((BigDecimal) value).doubleValue()));
        }
        readings.setObservations(observations);
        String key = jsonObject.getString("key");
        key = key.replace(".", ""); // PM2.5 zapisujemy jako PM25 (enum)
        readings.setKey(PollutionType.valueOf(key));
        return readings;
    } // trzeba poprawic nie zapisuje key

    /*
    sensors
     */
    public String readStationSensors(int stationId) {
        String sensors_tail = "station/sensors/" + stationId;
        URLstring = URLstring + sensors_tail;
        Connection connection = new Connection(URLstring);
        return connection.getData();
    }
    public List<StationSensors> parseStationSensors(String data) {
        List<StationSensors> sensors = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);

        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject current = jsonArray.getJSONObject(i);
            JSONObject current_param = new JSONObject(current.get("param").toString());
            StationSensors sensor = new StationSensors();
            sensor.setSensorID(current.getInt("id"));
            sensor.setStationID(current.getInt("stationId"));
            String key = current_param.getString("paramCode");
            key = key.replace(".", ""); // PM2.5 zapisujemy jako PM25 (enum)
            sensor.setKey(PollutionType.valueOf(key));
            sensors.add(sensor);
        }
        return sensors;
    }

    /*
    getIndex
     */
    public String readGetIndex(int stationId) {
        String index_tail = "aqindex/getIndex/" + stationId;
        URLstring = URLstring + index_tail;
        Connection connection = new Connection(URLstring);
        return connection.getData();
    }
    public List<IndexData> parseGetIndex(String data) {
        List<IndexData> indexes = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);

        String[] firstPart = {"st", "so2", "no2", "co", "pm10", "pm25", "o3", "c6h6"};
        String[] secondPart = {"SourceDataDate", "CalcDate", "IndexLevel"};
        PollutionType[] firstPartAsEnum = {PollutionType.STANDARD, PollutionType.SO2, PollutionType.NO2,
                PollutionType.CO, PollutionType.PM10, PollutionType.PM25, PollutionType.O3, PollutionType.C6H6};

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (int i=0; i<firstPart.length; i++) {
            PollutionType key = firstPartAsEnum[i];
            LocalDateTime[] localDateTimes = new LocalDateTime[2];
            for (int j=0; j<2; j++) {
                String dateString = jsonObject.get(firstPart[i] + secondPart[j]).toString();
                try { // api czasami pokazuje date inaczej
                    long epochDate = Long.parseLong(dateString);
                    localDateTimes[j] = LocalDateTime.ofEpochSecond(epochDate/1000, 0,
                            ZoneId.of("Europe/Warsaw").getRules().getOffset(LocalDateTime.now()));
                } catch (NumberFormatException e) {
                    if (dateString.equals(JSONObject.NULL.toString())) {
                        localDateTimes[j] = null;
                    } else {
                        localDateTimes[j] = LocalDateTime.parse(dateString, formatter);
                    }
                }
            }
            Object indexLevel;
            if (jsonObject.get(firstPart[i] + "IndexLevel")==JSONObject.NULL) { // w przypadku braku indeksu
                indexLevel = -1;
            } else {
                JSONObject indexLevelInfo = jsonObject.getJSONObject(firstPart[i] + "IndexLevel");
                indexLevel = indexLevelInfo.get("id");
            }

            indexes.add(new IndexData(localDateTimes[0], localDateTimes[1],
                    new IndexData.IndexLevel((int) indexLevel), key));
        }

        return indexes;
    }

    public static void main(String[] args) {

    }
}
