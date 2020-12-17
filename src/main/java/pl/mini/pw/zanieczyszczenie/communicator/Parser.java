package pl.mini.pw.zanieczyszczenie.communicator;


import org.json.JSONArray;
import org.json.JSONObject;
import pl.mini.pw.zanieczyszczenie.data.commons.*;
import pl.mini.pw.zanieczyszczenie.data.dataclasses.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Parser {
    private String URLstring = "http://api.gios.gov.pl/pjp-api/rest/";

    /*
    FindAll
     */
    private String readFindAll() {
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
    private String readReadings(int sensorId) {
        String getDataURL_tail = "data/getData/" + sensorId;
        URLstring = URLstring + getDataURL_tail;
        Connection connection = new Connection(URLstring);
        return connection.getData();
    }
    public List<Readings.Observation> parseReadings(String data) {
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
        return observations;
    } // trzeba poprawic nie zapisuje key

    /*
    sensors
     */
    private String readStationSensors(int stationId) {
        String sensors_tail = "station/sensors/" + stationId;
        URLstring = URLstring + sensors_tail;
        Connection connection = new Connection(URLstring);
        return connection.getData();
    }
    private List<StationSensors> parseStationSensors(String data) {
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

    public static void main(String[] args) {

    }
}
