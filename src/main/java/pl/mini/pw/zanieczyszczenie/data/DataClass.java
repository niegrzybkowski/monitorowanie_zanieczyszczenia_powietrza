package pl.mini.pw.zanieczyszczenie.data;

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
}
