package pl.mini.pw.zanieczyszczenie.data;

import java.util.List;

public class FindAll extends DataClass {
    // Parsed response from GIOS API
    public static class StationData {
        // TODO: remove unnecessary fields
        private int id;
        private String stationName;
        private double geographicLat;
        private double geographicLon;
        private int cityID;
        private String cityName;
        private String communeName;
        private String districtName;
        private String provinceName;
        private String addressStreet;

        public StationData(int id, String stationName, double geographicLat, double geographicLon, int cityID, String cityName, String communeName, String districtName, String provinceName, String addressStreet) {
            this.id = id;
            this.stationName = stationName;
            this.geographicLat = geographicLat;
            this.geographicLon = geographicLon;
            this.cityID = cityID;
            this.cityName = cityName;
            this.communeName = communeName;
            this.districtName = districtName;
            this.provinceName = provinceName;
            this.addressStreet = addressStreet;
        }

        public int getId() {
            return id;
        }

        public String getStationName() {
            return stationName;
        }

        public double getGeographicLat() {
            return geographicLat;
        }

        public double getGeographicLon() {
            return geographicLon;
        }

        public int getCityID() {
            return cityID;
        }

        public String getCityName() {
            return cityName;
        }

        public String getCommuneName() {
            return communeName;
        }

        public String getDistrictName() {
            return districtName;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public String getAddressStreet() {
            return addressStreet;
        }
    }

    private List<StationData> container;
}
