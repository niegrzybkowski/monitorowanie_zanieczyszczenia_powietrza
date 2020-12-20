package pl.mini.pw.zanieczyszczenie.communicator.pages;

import pl.mini.pw.zanieczyszczenie.data.commons.PollutionType;

import java.time.LocalDateTime;
import java.util.List;

public class Index extends APIPage {
    /*
        Indeks jakości powietrza:
        pjp-api/rest/aqindex/getIndex/{stationId}
     */
    private List<IndexData> indexes;

    public Index(List<IndexData> indexes) {
        this.indexes = indexes;
    }

    public List<IndexData> getIndexes() {
        return indexes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Index index = (Index) o;

        return indexes.equals(index.indexes);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + indexes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Index{" +
                super.toString() + ", " +
                indexes +
                '}';
    }

    public static class IndexData {
        public final LocalDateTime sourceDataDate;
        public final LocalDateTime calculationDate;
        public final IndexLevel indexLevel;
        public final PollutionType key;

        public IndexData(LocalDateTime sourceDataDate, LocalDateTime calculationDate,
                         IndexLevel indexLevel, PollutionType key) {
            this.sourceDataDate = sourceDataDate;
            this.calculationDate = calculationDate;
            this.indexLevel = indexLevel;
            this.key = key;
        }

        @Override
        public String toString() {
            return "IndexData{" +
                    "sourceDataDate=" + sourceDataDate +
                    ", calculationDate=" + calculationDate +
                    ", indexLevel=" + indexLevel +
                    ", key=" + key +
                    '}';
        }

        public static class IndexLevel {
            private final int id;

            public IndexLevel(int id) {
                this.id = id;
            }

            @Override
            public String toString() {
                return "IndexLevel{" +
                        "id=" + id +
                        ", name=" + name() +
                        '}';
            }

            public String name(){
                switch (id) {
                    case 0:
                        return "Bardzo dobry";
                    case 1:
                        return "Dobry";
                    case 2:
                        return "Umiarkowany";
                    case 3:
                        return "Dostateczny";
                    case 4:
                        return "Zły";
                    case 5:
                        return "Bardzo zły";
                    default:
                        return "Brak indeksu";
                }
            }
        }
    }
}
