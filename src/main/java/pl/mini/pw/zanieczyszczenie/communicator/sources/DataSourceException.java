package pl.mini.pw.zanieczyszczenie.communicator.sources;

public class DataSourceException extends Exception {
    public DataSourceException(String message, Throwable err) {
        super(message, err);
    }
}
