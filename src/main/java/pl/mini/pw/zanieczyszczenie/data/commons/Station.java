package pl.mini.pw.zanieczyszczenie.data.commons;

public class Station {
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
