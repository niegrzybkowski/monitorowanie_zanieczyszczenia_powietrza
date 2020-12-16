package pl.mini.pw.zanieczyszczenie.communicator;


import org.json.JSONArray;
import org.json.JSONObject;
import pl.mini.pw.zanieczyszczenie.data.commons.Station;


import java.util.ArrayList;
import java.util.List;


public class Parser {
    private String URLstring = "http://api.gios.gov.pl/pjp-api/rest/";

    /*
    FindAll
     */
    public List<Station> readAndParseFindAll() {
        String findAllURL_tail = "station/findAll";
        URLstring = URLstring + findAllURL_tail;
        Connection connection = new Connection(URLstring);
        String data = connection.getData();

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


    public static void main(String[] args) {

    }
}
