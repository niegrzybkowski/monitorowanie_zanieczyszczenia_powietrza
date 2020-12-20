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

        public final int id;
        public final String stationName;
        public final double geographicLat;
        public final double geographicLon;
        public final int cityID;
        public final String cityName;
        public final String communeName;
        public final String districtName;
        public final String provinceName;
        public final String addressStreet;

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
    }
}
