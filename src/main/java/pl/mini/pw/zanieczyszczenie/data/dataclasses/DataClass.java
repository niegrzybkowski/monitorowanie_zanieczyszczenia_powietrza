package pl.mini.pw.zanieczyszczenie.data.dataclasses;

import java.time.LocalDateTime;

public abstract class DataClass {
    private LocalDateTime updated;

    public DataClass() {
        this.updated = LocalDateTime.now();
    }

    public void update() {
        this.updated = LocalDateTime.now();
    }

    public LocalDateTime getUpdateTime() {
        return updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataClass dataClass = (DataClass) o;

        return updated.equals(dataClass.updated);
    }

    @Override
    public int hashCode() {
        return updated.hashCode();
    }

    @Override
    public String toString() {
        return "DataClass{" +
                "timestamp=" + updated +
                '}';
    }
}
