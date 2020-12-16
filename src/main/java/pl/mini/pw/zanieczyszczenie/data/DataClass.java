package pl.mini.pw.zanieczyszczenie.data;

import java.time.LocalDateTime;

public abstract class DataClass {
    private LocalDateTime updated;

    public LocalDateTime getUpdated() {
        return updated;
    }
}
