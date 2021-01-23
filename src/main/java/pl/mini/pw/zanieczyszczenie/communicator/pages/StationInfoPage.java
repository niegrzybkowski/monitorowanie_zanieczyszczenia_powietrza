package pl.mini.pw.zanieczyszczenie.communicator.pages;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;

public class StationInfoPage extends APIPage{
    private List<IndexPage.IndexData> indexes;
    private final int id;
    private final double geographicLat;
    private final double geographicLon;

    public StationInfoPage(List<IndexPage.IndexData> indexes, int id, double geographicLat, double geographicLon) {
        this.indexes = indexes;
        this.id = id;
        this.geographicLat = geographicLat;
        this.geographicLon = geographicLon;
    }

    public Color color() {
        int id = indexes.stream()
                .filter(a -> a.getKey().equals("st"))
                .findFirst()
                .map(a -> a.getIndexLevel())
                .orElse(-1);

        switch (id) {
            case 0:
                return Color.web("#00cc00");
            case 1:
                return Color.web("#00ff00");
            case 2:
                return Color.web("#ffff00");
            case 3:
                return Color.web("#ff6600");
            case 4:
                return Color.web("#ff3300");
            default: {
                return Color.web("#737373");
            }
        }
    }


    public List<IndexPage.IndexData> getIndexes() {
        return indexes;
    }

    public int getId() {
        return id;
    }

    public double getGeographicLat() {
        return geographicLat;
    }

    public double getGeographicLon() {
        return geographicLon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationInfoPage)) return false;
        if (!super.equals(o)) return false;
        StationInfoPage that = (StationInfoPage) o;
        return id == that.id && Double.compare(that.geographicLat, geographicLat) == 0 && Double.compare(that.geographicLon, geographicLon) == 0 && Objects.equals(indexes, that.indexes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), indexes, id, geographicLat, geographicLon);
    }

    @Override
    public String toString() {
        return "StationInfoPage{" +
                "indexes=" + indexes +
                ", id=" + id +
                ", geographicLat=" + geographicLat +
                ", geographicLon=" + geographicLon +
                '}';
    }
}
