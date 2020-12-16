package pl.mini.pw.zanieczyszczenie.data.commons;

import java.time.LocalDateTime;

public class IndexData {
    public final LocalDateTime sourceDataDate;
    public final LocalDateTime calculationDate;
    public final IndexLevel indexLevel;

    public IndexData(LocalDateTime sourceDataDate, LocalDateTime calculationDate, IndexLevel indexLevel) {
        this.sourceDataDate = sourceDataDate;
        this.calculationDate = calculationDate;
        this.indexLevel = indexLevel;
    }

    public static class IndexLevel {
        private int id;
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
