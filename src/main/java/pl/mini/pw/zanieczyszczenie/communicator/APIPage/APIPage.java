package pl.mini.pw.zanieczyszczenie.communicator.APIPage;

import java.time.LocalDateTime;

public abstract class APIPage {
    private LocalDateTime updated;

    public APIPage() {
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

        APIPage dataClass = (APIPage) o;

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
