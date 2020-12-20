package pl.mini.pw.zanieczyszczenie.data.APIPage;

import pl.mini.pw.zanieczyszczenie.data.commons.IndexData;

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
}
