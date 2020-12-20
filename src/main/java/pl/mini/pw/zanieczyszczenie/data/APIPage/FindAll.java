package pl.mini.pw.zanieczyszczenie.data.APIPage;

import pl.mini.pw.zanieczyszczenie.data.commons.Station;

import java.util.List;

public class FindAll extends DataClass {
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
}
