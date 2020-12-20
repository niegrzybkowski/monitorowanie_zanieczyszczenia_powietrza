package pl.mini.pw.zanieczyszczenie.communicator.pages;


import java.util.List;

public class FindAll extends APIPage {
    /*
        Stacje pomiarowe:
        pjp-api/rest/station/findAll
     */
    private List<Station> container;

    public FindAll(List<Station> container) {
        this.container = container;
    }

    public List<Station> getContainer() {
        return container;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FindAll findAll = (FindAll) o;

        return container.equals(findAll.container);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + container.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FindAll{" +
                super.toString() + ", " +
                container +
                "}";
    }

    public static class Station {
        // TODO: remove unnecessary fields

        private final int id;
        private final String stationName;
        private final double geographicLat;
        private final double geographicLon;
        private final int cityID;
        private final String cityName;
        private final String communeName;
        private final String districtName;
        private final String provinceName;
        private final String addressStreet;

        public Station(int id, String stationName, double geographicLat, double geographicLon, int cityID, String cityName, String communeName, String districtName, String provinceName, String addressStreet) {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Station station = (Station) o;

            if (id != station.id) return false;
            if (Double.compare(station.geographicLat, geographicLat) != 0) return false;
            if (Double.compare(station.geographicLon, geographicLon) != 0) return false;
            if (cityID != station.cityID) return false;
            if (!stationName.equals(station.stationName)) return false;
            if (!cityName.equals(station.cityName)) return false;
            if (!communeName.equals(station.communeName)) return false;
            if (!districtName.equals(station.districtName)) return false;
            if (!provinceName.equals(station.provinceName)) return false;
            return addressStreet != null ? addressStreet.equals(station.addressStreet) : station.addressStreet == null;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = id;
            result = 31 * result + stationName.hashCode();
            temp = Double.doubleToLongBits(geographicLat);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(geographicLon);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            result = 31 * result + cityID;
            result = 31 * result + cityName.hashCode();
            result = 31 * result + communeName.hashCode();
            result = 31 * result + districtName.hashCode();
            result = 31 * result + provinceName.hashCode();
            result = 31 * result + (addressStreet != null ? addressStreet.hashCode() : 0);
            return result;
        }
    }
}
