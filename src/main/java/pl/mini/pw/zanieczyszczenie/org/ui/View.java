package pl.mini.pw.zanieczyszczenie.org.ui;

public interface View {
    void drawDots(
            // customowy obiekt z modelu pewnie
    );
    void drawLastUpdate(
            String text // lub datetime, ale imo lepiej już przekazać sformatowany
    );
    void drawLoading(
            // stan ładowania, pewnie enum
    );
    void drawIndicators(
            // obiekt z modelu pewnie
    );
    void drawPlot(
            // nw co tutaj
    );
    void drawLargePlot(
            // tutaj tym bardziej
    );
}
