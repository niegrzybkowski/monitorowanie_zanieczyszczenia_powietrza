package pl.mini.pw.zanieczyszczenie.data.commons;

import java.time.LocalDateTime;

public class IndexData {
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
